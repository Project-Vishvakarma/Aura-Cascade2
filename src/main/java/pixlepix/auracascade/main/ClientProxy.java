package pixlepix.auracascade.main;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import pixlepix.auracascade.KeyBindings;
import pixlepix.auracascade.block.entity.EntityFairy;
import pixlepix.auracascade.block.tile.AuraTilePedestal;
import pixlepix.auracascade.data.CoordTuple;
import pixlepix.auracascade.lexicon.*;
import pixlepix.auracascade.main.event.ClientEventHandler;
import pixlepix.auracascade.render.OverlayRender;
import pixlepix.auracascade.render.RenderEntityFairy;
import pixlepix.auracascade.render.RenderPedestal;

public class ClientProxy extends CommonProxy {


    @Override
    public World getWorld() {
        return Minecraft.getMinecraft().theWorld;
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        FMLCommonHandler.instance().bus().register(new ClientTickHandler());
        MinecraftForge.EVENT_BUS.register(new OverlayRender());

        KeyBindings.init();

        ClientEventHandler clientEventHandler = new ClientEventHandler();
        MinecraftForge.EVENT_BUS.register(clientEventHandler);
        FMLCommonHandler.instance().bus().register(clientEventHandler);


    }

    @Override
    public void addToTutorial(LexiconEntry entry) {

        GuiLexicon.tutorialMaster.add(entry);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        ClientRegistry.bindTileEntitySpecialRenderer(AuraTilePedestal.class, new RenderPedestal());
        RenderingRegistry.registerEntityRenderingHandler(EntityFairy.class, new RenderEntityFairy());
    }

    @Override
    public EntityPlayer getPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    @Override
    public EffectRenderer getEffectRenderer() {
        return Minecraft.getMinecraft().effectRenderer;
    }


    @Override
    public void setEntryToOpen(LexiconEntry entry) {
        GuiLexicon.currentOpenLexicon = new GuiLexiconEntry(entry, new GuiLexiconIndex(entry.category));
    }


    public void addBlockDestroyEffects(CoordTuple tuple) {

        Minecraft.getMinecraft().effectRenderer.addBlockDestroyEffects(tuple.getX(), tuple.getY(), tuple.getZ(), tuple.getBlock(Minecraft.getMinecraft().theWorld), tuple.getMeta(Minecraft.getMinecraft().theWorld));


    }

    @Override
    public void setLexiconStack(ItemStack stack) {
        GuiLexicon.stackUsed = stack;
    }

    @Override
    public void addEffectBypassingLimit(EntityFX entityFX) {
        if (Config.overrideMaxParticleLimit) {
            Minecraft.getMinecraft().effectRenderer.fxLayers[entityFX.getFXLayer()].add(entityFX);
        } else {
            Minecraft.getMinecraft().theWorld.spawnEntityInWorld(entityFX);
        }
    }
}
