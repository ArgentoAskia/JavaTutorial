package cn.argento.askia.effective.java.forty;

/**
 * 正确的例子，提供非内部化的比较
 */
public class StudentRight implements Comparable<StudentRight>{

    private final Person person; // 组合一个Person实例
    private final int grade;

    public StudentRight(String name, int age, int grade) {
        this.person = new Person(name, age);
        this.grade = grade;
    }

    // 提供"视图"方法，返回Person对象
    public Person asPerson() {
        return person;
    }

    // 在 StudentWithView 自己的 compareTo 中，实现完整的比较逻辑
    @Override
    public int compareTo(StudentRight other) {
        // 1. 先比较内部的Person（年龄）
        int personComparison = this.person.compareTo(other.person);
        if (personComparison != 0) {
            return personComparison;
        }
        // 2. 如果年龄相同，再比较年级
        return Integer.compare(this.grade, other.grade);
    }

    @Override
    public String toString() {
        return person.toString() + "-G" + grade;
    }
}