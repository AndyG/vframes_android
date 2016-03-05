package data.model.character.bnb;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BreadAndButterModel {

    private Map<String, List<BreadAndButterCombo>> breadAndButters;

    public BreadAndButterModel(LinkedHashMap<String, List<BreadAndButterCombo>> breadAndButters) {
        this.breadAndButters = breadAndButters;
    }

    public Map<String, List<BreadAndButterCombo>> getBreadAndButters() {
        return breadAndButters;
    }
}
