package com.check.thread.plugin

class FilterClass{
    private  static HashSet<String> exclude;
    static {
        exclude = new HashSet<>()
        exclude.add("android.support")
        //本地不能修改的代码
        exclude.add("com.example.replacecode")
    }

    /**
     * 根据className 判断该类是否能修改
     * @param className 类名
     * @return false 不能修改 true可以修改（但是不一定会修改）
     */
    protected static boolean canModify(String className){
        Iterator<String> iterator = exclude.iterator()
        while (iterator.hasNext()){
            String packageName = iterator.next()
            if(className.startsWith(packageName)){
                return false
            }
        }

        //编译过程中的关键文件不能修改
        if (className.contains('R$') ||
                className.contains('R2$') ||
                className.contains('R.class') ||
                className.contains('R2.class') ||
                className.contains('BuildConfig.class')) {
            return false
        }
        return true
    }
}