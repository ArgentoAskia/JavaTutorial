package cn.argento.askia.effective.java.forty;

/**
 * 错误的例子，不要使用继承
 */
public class StudentError extends Person {
    private final int grade; // 新增加的属性：年级

    public StudentError(String name, int age, int grade) {
        super(name, age);
        this.grade = grade;
    }

    // 错误：尝试扩展比较逻辑，但破坏了约定
    @Override
    public int compareTo(Person other) {
        // 首先，使用Person的比较逻辑（按年龄比）
        int ageComparison = super.compareTo(other);

        // 如果年龄相同，并且 other 也是 StudentError，则按年级比
        if (ageComparison == 0 && other instanceof StudentError) {
            return Integer.compare(this.grade, ((StudentError) other).grade);
        }

        // 如果年龄不同，或者 other 不是 StudentError，则返回年龄比较结果
        return ageComparison;
    }

    @Override
    public String toString() {
        return super.toString() + "-G" + grade;
    }
}
