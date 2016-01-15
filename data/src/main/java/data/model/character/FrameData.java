package data.model.character;

import java.util.List;
import java.util.Map;

import data.model.move.IFrameDataEntry;
import data.model.move.MoveCategory;

/**
 * Created by andy on 1/14/16
 */
public class FrameData {

    private Map<MoveCategory, List<IFrameDataEntry>> categoriesMap;

    public FrameData(Map<MoveCategory, List<IFrameDataEntry>> categoriesMap) {
        this.categoriesMap = categoriesMap;
    }

    public boolean hasCategory(MoveCategory moveCategory) {
        return categoriesMap.containsKey(moveCategory);
    }

    public List<IFrameDataEntry> getFromCategory(MoveCategory category) {
        return categoriesMap.get(category);
    }

}
