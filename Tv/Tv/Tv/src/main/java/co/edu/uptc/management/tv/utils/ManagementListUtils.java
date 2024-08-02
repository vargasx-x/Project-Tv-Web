package co.edu.uptc.management.library.utils;

import java.lang.reflect.Field;
import java.util.List;

public class ManagementListUtils<T> {
	private List<T> listObjects;
	
    public ManagementListUtils(List<T> listObjects) {
        this.listObjects = listObjects;
    }

    public void sortList(String ... attributeNames) throws NoSuchFieldException, IllegalAccessException {
        for (int i = 0; i < this.listObjects.size() - 1; i++) {
            for (int j = 0; j < (this.listObjects.size() - i - 1); j++) {
                T obj1 = this.listObjects.get(j);
                T obj2 = this.listObjects.get(j + 1);
                int comparison = compareAttributes(obj1, obj2, attributeNames);
                if (comparison > 0) {
                    T temp = this.listObjects.get(j);
                    this.listObjects.set(j, this.listObjects.get(j + 1));
                    this.listObjects.set(j + 1, temp);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private int compareAttributes(T obj1, T obj2, String... attributeNames) throws NoSuchFieldException, IllegalAccessException {
        for (String attributeName : attributeNames) {
            Field field = obj1.getClass().getDeclaredField(attributeName);
            field.setAccessible(true);
			Comparable<Object> value1 = (Comparable<Object>) field.get(obj1);
            Comparable<Object> value2 = (Comparable<Object>) field.get(obj2);
            int comparison = value1.compareTo(value2);
            if (comparison != 0) {
                return comparison;
            }
        }
        return 0;
    }

    public T findObjectBinary(T findObject, String... attributeNames) throws NoSuchFieldException, IllegalAccessException {
        sortList(attributeNames);
        int first = 0;
        int last = listObjects.size() - 1;
        while (first <= last) {
            int middle = (first + last) / 2;
            T middleObject = listObjects.get(middle);
            int comparison = compareAttributes(middleObject, findObject, attributeNames);
            if (comparison == 0) {
                return middleObject;
            } else if (comparison > 0) {
                last = middle - 1;
            } else {
                first = middle + 1;
            }
        }
        return null;
    }

    public List<T> getListObjects() {
        return listObjects;
    }
}
