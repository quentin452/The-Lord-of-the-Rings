package lotr.common.coremod;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import io.gitlab.dwarfyassassin.lotrucp.core.UCPCoreMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@IFMLLoadingPlugin.TransformerExclusions({"lotr.common.coremod", "io.gitlab.dwarfyassassin.lotrucp.core"})
@IFMLLoadingPlugin.SortingIndex(1001)
@IFMLLoadingPlugin.MCVersion("1.7.10")
public class LOTRLoadingPlugin implements IFMLLoadingPlugin {
    public UCPCoreMod dwarfyAssassinCompatibilityCoremod = new UCPCoreMod();
    public static Logger logger = LogManager.getLogger("LOTRLoadingPlugin");

    @Override
    public String[] getASMTransformerClass() {
        logger.info("loaded getASMTransformerClass class");
        ArrayList<String> classes = new ArrayList<>();
        classes.add(LOTRClassTransformer.class.getName());
        classes.addAll(Arrays.asList(dwarfyAssassinCompatibilityCoremod.getASMTransformerClass()));
        return classes.toArray(new String[0]);
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return dwarfyAssassinCompatibilityCoremod.getSetupClass();
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
