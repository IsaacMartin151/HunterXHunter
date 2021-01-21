package com.chubbychump.hunterxhunter.common.entities.entityclasses;

import com.chubbychump.hunterxhunter.common.entities.LungeGoal;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.entity.WitherRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.ZombieEvent;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = IChargeableMob.class
)
public class Youpi extends MonsterEntity implements IChargeableMob, IRangedAttackMob {
    private static final DataParameter<Integer> FIRST_HEAD_TARGET = EntityDataManager.createKey(Youpi.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> INVULNERABILITY_TIME = EntityDataManager.createKey(Youpi.class, DataSerializers.VARINT);

    private int nextHeadUpdate = 0;
    private float lastexplosion = this.getMaxHealth();
    private int explosionCountdown = 0;
    public int leapTimer = 0;
    private boolean immunearrows = false;
    private ServerBossInfo bossInfo = (ServerBossInfo)(new ServerBossInfo(this.getDisplayName(), BossInfo.Color.RED, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);
    private static final Predicate<LivingEntity> NOT_UNDEAD = (p_213797_0_) -> {
        return p_213797_0_.getCreatureAttribute() != CreatureAttribute.UNDEAD && p_213797_0_.attackable();
    };
    private static final EntityPredicate ENEMY_CONDITION = (new EntityPredicate()).setDistance(20.0D).setCustomPredicate(NOT_UNDEAD);

    public Youpi(EntityType<? extends Youpi> youpi, World world) {
        super(youpi, world);
        this.setHealth(this.getMaxHealth());
        this.getNavigator().setCanSwim(true);
        this.experienceValue = 50;
        //this.setCustomName(ITextComponent.getTextComponentOrEmpty("Youpi"));
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new Youpi.DoNothingGoal());
        //this.goalSelector.addGoal(1, new LeapAtTargetGoal(this, 12.0f));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));

        this.goalSelector.addGoal(20, new LungeGoal(this, 1.5f));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 50.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(FIRST_HEAD_TARGET, 0);
        this.dataManager.register(INVULNERABILITY_TIME, 0);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Invul", this.getInvulTime());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setInvulTime(compound.getInt("Invul"));
        if (this.hasCustomName()) {
            this.bossInfo.setName(this.getDisplayName());
        }

    }

    public void setCustomName(@Nullable ITextComponent name) {
        super.setCustomName(name);
        this.bossInfo.setName(this.getDisplayName());
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ZOGLIN_AMBIENT;
    }
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_PANDA_STEP;
    }
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_EVOKER_PREPARE_ATTACK;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 3.74F;
    }

    protected void jump() {
        Vector3d vector3d = this.getMotion();
        this.setMotion(vector3d.x * 1.3, vector3d.y + 1, vector3d.z * 1.3);
        this.isAirBorne = true;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void livingTick() {

        Vector3d vector3d = this.getMotion();
        if (horizontalMag(vector3d) > 0.05D) {
            this.rotationYaw = (float)MathHelper.atan2(vector3d.z, vector3d.x) * (180F / (float)Math.PI) - 90.0F;
        }

        super.livingTick();

        boolean flag = this.isCharged();

        if (this.getInvulTime() > 0) {
            for(int i1 = 0; i1 < 3; ++i1) {
                this.world.addParticle(ParticleTypes.ENTITY_EFFECT, this.getPosX() + this.rand.nextGaussian(), this.getPosY() + (double)(this.rand.nextFloat() * 3.3F), this.getPosZ() + this.rand.nextGaussian(), (double)0.7F, (double)0.7F, (double)0.9F);
            }
        }
    }

    protected void updateAITasks() {
        if (this.getInvulTime() > 0) {
            int j1 = this.getInvulTime() - 1;
            if (j1 <= 0) {
                Explosion.Mode explosion$mode = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this) ? Explosion.Mode.DESTROY : Explosion.Mode.NONE;
                this.world.createExplosion(this, this.getPosX(), this.getPosYEye(), this.getPosZ(), 9.0F, true, explosion$mode);
                if (!this.isSilent()) {
                    this.world.playBroadcastSound(1023, this.getPosition(), 0);
                }
                this.immunearrows = false;
            }
            this.setInvulTime(j1);
        } else {
            super.updateAITasks();

            if (this.ticksExisted >= this.nextHeadUpdate) {
                this.nextHeadUpdate = this.ticksExisted + 10 + this.rand.nextInt(10);

                int k1 = this.getWatchedTargetId();
                if (k1 > 0) {
                    Entity entity = this.world.getEntityByID(k1);
                    if (entity != null && entity.isAlive() && (this.getDistanceSq(entity) > 50.0D) && this.canEntityBeSeen(entity)) {
                        if (entity instanceof PlayerEntity && ((PlayerEntity) entity).abilities.disableDamage) {
                            this.updateWatchedTargetId(0);
                        } else {
                            //this.launchWitherSkullToEntity(2, (LivingEntity) entity);
                            //this.attackEntityAsMob(entity);
                            //Leap at them
                            //Vector3d vector3d1 = new Vector3d(entity.getPosX() - this.getPosX(), 0.0D, entity.getPosZ() - this.getPosZ());

                            //this.setMotion(vector3d1.x, 3.0D, vector3d1.z);

                            //Set cooldown
                            this.nextHeadUpdate = this.ticksExisted + 300;
                        }
                    } else {
                        this.updateWatchedTargetId(0);
                    }
                } else {
                    List<LivingEntity> list = this.world.getTargettableEntitiesWithinAABB(PlayerEntity.class, ENEMY_CONDITION, this, this.getBoundingBox().grow(40.0D, 12.0D, 40.0D));

                    for (int j2 = 0; j2 < 10 && !list.isEmpty(); ++j2) {
                        LivingEntity livingentity = list.get(this.rand.nextInt(list.size()));
                        if (livingentity != this && livingentity.isAlive() && this.canEntityBeSeen(livingentity)) {
                            if (!((PlayerEntity) livingentity).abilities.disableDamage) {
                                this.updateWatchedTargetId(livingentity.getEntityId());
                            }
                            this.updateWatchedTargetId(livingentity.getEntityId());
                            break;
                        }

                        list.remove(livingentity);
                    }
                }
            }

            if (this.getAttackTarget() != null) {
                this.updateWatchedTargetId(this.getAttackTarget().getEntityId());
            } else {
                this.updateWatchedTargetId(0);
            }

            this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
        }
    }

    @Deprecated //Forge: DO NOT USE use BlockState.canEntityDestroy
    public static boolean canDestroyBlock(BlockState blockIn) {
        //TODO: can't destroy blocks that make up the islands, can destroy everything else
        return false;
    }

    /**
     * Initializes this Wither's explosion sequence and makes it invulnerable. Called immediately after spawning.
     */
    public void ignite() {
        this.setInvulTime(140);
    }

    public void setMotionMultiplier(BlockState state, Vector3d motionMultiplierIn) {
    }

    /**
     * Add the given player to the list of players tracking this entity. For instance, a player may track a boss in order
     * to view its associated boss bar.
     */
    public void addTrackingPlayer(ServerPlayerEntity player) {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    /**
     * Removes the given player from the list of players tracking this entity. See {@link Entity#addTrackingPlayer} for
     * more information on tracking.
     */
    public void removeTrackingPlayer(ServerPlayerEntity player) {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }


    /**
     * Attack the specified entity using a ranged attack.
     */
    public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
        //this.launchWitherSkullToEntity(0, target);
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (source != DamageSource.DROWN && !(source.getTrueSource() instanceof Youpi)) {
            if (this.getInvulTime() > 0 && source != DamageSource.OUT_OF_WORLD) {
                return false;
            } else {
                if (this.isCharged()) {
                    Entity entity = source.getImmediateSource();
                    if (entity instanceof AbstractArrowEntity) {
                        return false;
                    }
                }

                Entity entity1 = source.getTrueSource();
                if (entity1 != null && !(entity1 instanceof PlayerEntity) && entity1 instanceof LivingEntity) {
                    return false;
                } else {
                    return super.attackEntityFrom(source, amount);
                }
            }
        } else {
            return false;
        }
    }

    protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropSpecialItems(source, looting, recentlyHitIn);
        ItemEntity itementity = this.entityDropItem(Items.NETHER_STAR);
        if (itementity != null) {
            itementity.setNoDespawn();
        }

    }

    /**
     * Makes the entity despawn if requirements are reached
     */
    public void checkDespawn() {
        this.idleTime = 0;
    }

    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    public boolean addPotionEffect(EffectInstance effectInstanceIn) {
        return false;
    }

    public int getInvulTime() {
        return this.dataManager.get(INVULNERABILITY_TIME);
    }

    public void setInvulTime(int time) {
        this.dataManager.set(INVULNERABILITY_TIME, time);
    }

    /**
     * Returns the target entity ID if present, or -1 if not @param par1 The target offset, should be from 0-2
     */
    public int getWatchedTargetId() {
        return this.dataManager.get(FIRST_HEAD_TARGET);
    }

    /**
     * Updates the target entity ID
     */
    public void updateWatchedTargetId(int newId) {
        this.dataManager.set(FIRST_HEAD_TARGET, newId);
    }

    public boolean isCharged() {
        float yeet = this.lastexplosion - 50.0F;
        if (this.explosionCountdown <= 0 && this.getHealth() <= yeet) {
            this.explosionCountdown = 100;
        }
        return this.getHealth() <= yeet;

    }

    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEFINED;
    }

    protected boolean canBeRidden(Entity entityIn) {
        return false;
    }

    /**
     * Returns false if this Entity is a boss, true otherwise.
     */
    public boolean isNonBoss() {
        return false;
    }

    public boolean isPotionApplicable(EffectInstance potioneffectIn) {
        return false;
    }

    class DoNothingGoal extends Goal {
        public DoNothingGoal() {
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            //return WitherEntity.this.getInvulTime() > 0;
            //TODO: set this to true when the player gets close enough (Stop the prep music, start boss battle)
            return false;

        }
    }
}
