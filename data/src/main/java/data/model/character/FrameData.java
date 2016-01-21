package data.model.character;

import java.util.List;
import java.util.Map;

import data.model.move.IFrameDataEntryHolder;
import data.model.move.MoveCategory;

/**
 * Created by andy on 1/14/16
 */
public class FrameData {

    private Map<MoveCategory, List<IFrameDataEntryHolder>> categoriesMap;

    public FrameData(Map<MoveCategory, List<IFrameDataEntryHolder>> categoriesMap) {
        this.categoriesMap = categoriesMap;
    }

    public boolean hasCategory(MoveCategory moveCategory) {
        return categoriesMap.containsKey(moveCategory);
    }

    public List<IFrameDataEntryHolder> getFromCategory(MoveCategory category) {
        return categoriesMap.get(category);
    }

}
