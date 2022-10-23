package com.hisco.cmm.modules;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ModuleInfo {

    private static ModuleInfo instance = null;

    public static synchronized ModuleInfo getInstance() {

        if (instance == null) {
            instance = new ModuleInfo();
        }

        return instance;
    }

    private List<ModuleInfoDto> modulePool = null;

    public ModuleInfo() {
        modulePool = new ArrayList<ModuleInfoDto>();
    }

    public void Destroy() {
        clear();
    }

    public List<ModuleInfoDto> getPool() {
        return this.modulePool;
    }

    public void clear() {
        modulePool.clear();
    }

    public void sort() {

        Collections.sort(modulePool, new Comparator<ModuleInfoDto>() {
            @Override
            public int compare(ModuleInfoDto o1, ModuleInfoDto o2) {
                return o1.getAlign() - o2.getAlign();
            }
        });

    }

    public void put(String key, ModuleInfoDto value) {

        boolean exists = false;

        if (modulePool != null && modulePool.size() > 0) {
            try {
                for (ModuleInfoDto data : modulePool) {
                    if (key.equals(data.getKey())) {
                        exists = true;
                        break;
                    }
                }
            } catch (Exception e) {
                exists = modulePool.contains(value);
            }
        }

        if (!exists)
            modulePool.add(value);
    }

    public ModuleInfoDto get(String key) {

        if (modulePool != null && modulePool.size() > 0) {
            for (ModuleInfoDto data : modulePool) {
                if (key.equals(data.getKey()))
                    return data;
            }
        }

        return null;
    }

    public boolean containsKey(String key) {
        boolean exists = false;
        if (modulePool != null && modulePool.size() > 0) {
            for (ModuleInfoDto data : modulePool) {
                if (key.equals(data.getKey())) {
                    exists = true;
                    break;
                }
            }
        }
        return exists;
    }

    public int size() {
        return modulePool.size();
    }

    public void remove(String key) {
        if (modulePool != null && modulePool.size() > 0) {
            for (int i = 0; i < modulePool.size(); i++) {
                if (key.equals(modulePool.get(i).getKey())) {
                    modulePool.remove(i);
                }
            }
        }
    }

    public String toString() {
        return ObjectUtil.ConvertJsonString(modulePool);
    }
}
