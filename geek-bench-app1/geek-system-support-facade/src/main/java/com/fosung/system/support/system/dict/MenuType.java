package com.fosung.system.support.system.dict;
/**
 *
 * 资源菜单类型
 *
 * @date  2021/4/26 16:02
 * @version
*/
public enum MenuType {
    菜单("menu","1"),目录("catalog","2"),按钮("button","3");


    private String name;
    private String index;

    private MenuType(String name,String index){
        this.name=name;
        this.index=index;
    }

    public static String getIndex(String name){
        for (MenuType menuType : MenuType.values()){
            if(menuType.getName().equals(name)){
                return menuType.getIndex();
            }
        }
        return "1";
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
