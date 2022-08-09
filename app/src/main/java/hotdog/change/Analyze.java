package hotdog.change;

import com.github.gumtreediff.actions.EditScript;
import com.github.gumtreediff.actions.model.Action;

public class Analyze {

    public String generateChangeVector(EditScript editScript) {
        String changeVector = "";
        for (Action action : editScript.asList()) {
            String location;
            if (action.getName().contains("delete") || action.getName().contains("update"))
                location = "";
            else location = getActionLocation(action);

            changeVector += action.getName() + "@"
                    + action.getNode().getType().toString()
                    .replaceAll("\\:\\s\\S$", "")
                    .replaceAll("[0-9+,+0-9]", "")
                    .replaceAll("\\[", "")
                    .replaceAll("\\]","")
                    + location.replace(" ", "") + "|";
        }
        return changeVector;
    }

    private String getActionLocation(Action action) {
        String[] actionStringList = action.toString().split("\n");
        boolean checked = false;
        for (int i=0; i<actionStringList.length; i++) {
            if (checked) {
                return "2" + actionStringList[i]
                        .replaceAll("\\:\\s\\S$", "")
                        .replaceAll("[0-9+,+0-9]", "")
                        .replaceAll("\\[", "")
                        .replaceAll("\\]","");
            }
            if (actionStringList[i].contains("\\[")) continue;
            else if (actionStringList[i].indexOf("to") == 0) checked = true;
        }
        return "";
    }
}
