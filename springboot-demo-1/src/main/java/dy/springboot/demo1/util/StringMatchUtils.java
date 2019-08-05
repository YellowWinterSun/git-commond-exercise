package dy.springboot.demo1.util;

/**
 * 字符串匹配 工具类
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/7/22
 */
public class StringMatchUtils {

    /**
     * 动态规划实现的 通配符匹配算法
     * 允许 ?  * <br>
     *     ps: 不用indexOf是因为大计算的时候效率不及这套算法，其次使用该算法，可以允许用户在文本中存在不符合规则
     *     的${}存在,这些使用了占位符的原文档文本，不会被替换掉.
     * @param s 匹配字符
     * @param p 匹配规则
     * @author huangdy
     * @return 传入s，是否匹配规则p
     */
    public static boolean isMatch(String s, String p) {
        char[] str = s.toCharArray();
        char[] pattern = p.toCharArray();

        //replace multiple * with one *
        //e.g a**b --> a*b
        int writeIndex = 0;
        boolean isFirst = true;
        for (int i = 0; i < pattern.length; ++i){
            if (pattern[i] == '*'){
                if (isFirst){
                    pattern[writeIndex++] = pattern[i];
                    isFirst = false;
                }
            }
            else {
                pattern[writeIndex++] = pattern[i];
                isFirst = true;
            }
        }
        //code better faster than without this code in 10ms
        if (str.length > 0 && writeIndex > 0 && pattern[0] != '*' && pattern[0] != '?' && str[0] != pattern[0]){
            return false;
        }
        if (str.length > 0 && writeIndex > 0 && pattern[writeIndex-1] != '*' && pattern[writeIndex-1] != '?' && str[str.length-1] != pattern[writeIndex-1]){
            return false;
        }

        //begin the alo.
        boolean T[][] = new boolean[str.length+1][writeIndex+1];
        T[0][0] = true;     //because empty-string == empty-string
        if (writeIndex > 0 && pattern[0] == '*'){
            //because empty-string == *
            T[0][1] = true;
        }

        //DP deal with this problem
        for (int i = 1; i < T.length; ++i){
            for (int j = 1; j < T[0].length; ++j){
                if (pattern[j-1] == '?' || str[i-1] == pattern[j-1]){
                    T[i][j] = T[i-1][j-1];
                }
                else if (pattern[j-1] == '*'){
                    T[i][j] = T[i-1][j] || T[i][j-1];
                }
            }
        }

        return T[str.length][writeIndex];   //last one is answer
    }

}
