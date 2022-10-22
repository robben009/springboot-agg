

勾选项除了英文内容和最后两个其他全选。

参考示例：https://github.com/shuzijun/leetcode-editor/blob/master/doc/CustomCode_ZH.md

code filename:
$!velocityTool.camelCaseName(${question.titleSlug})


code tempalte(修改了包名称):
${question.content}

package leetcode.editor.cn;

//${question.title}
public class $!velocityTool.camelCaseName(${question.titleSlug}){
  public static void main(String[] args) {
       Solution solution = new $!velocityTool.camelCaseName(${question.titleSlug})().new Solution();
  }

${question.code}

}