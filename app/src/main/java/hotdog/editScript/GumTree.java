package hotdog.editScript;

import com.github.gumtreediff.actions.EditScript;
import com.github.gumtreediff.actions.EditScriptGenerator;
import com.github.gumtreediff.actions.SimplifiedChawatheScriptGenerator;
import com.github.gumtreediff.client.Run;
import com.github.gumtreediff.gen.python.PythonTreeGenerator;
import com.github.gumtreediff.matchers.CompositeMatchers;
import com.github.gumtreediff.matchers.MappingStore;
import com.github.gumtreediff.tree.Tree;
import com.github.gumtreediff.tree.TreeContext;

import java.io.IOException;

public class GumTree {

    public EditScript generateEditScript(String[] fileSources) {
        String srcFileSource = fileSources[0];
        String dstFileSource = fileSources[1];
        Run.initGenerators(); // registers the available parsers
        TreeContext srcTree = null, dstTree = null;
        try {
            srcTree = new PythonTreeGenerator().generateFrom().string(srcFileSource);
            dstTree = new PythonTreeGenerator().generateFrom().string(dstFileSource);
        } catch (Exception e) {
            return null;
        }
        EditScript actions = null;
        if (srcTree!= null && dstTree!=null) {
            Tree src = srcTree.getRoot();
            Tree dst = dstTree.getRoot();
            MappingStore mappings = new CompositeMatchers.SimpleGumtree().match(src, dst);
            EditScriptGenerator editScriptGenerator = new SimplifiedChawatheScriptGenerator();
            actions = editScriptGenerator.computeActions(mappings);
        }
        return actions;
    }
}
