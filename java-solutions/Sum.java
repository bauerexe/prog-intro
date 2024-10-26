public class Sum {
    public static void main(String[] args) {
        int sum = 0;
        StringBuilder builder_w_nums = new StringBuilder();
        for (String str : args) {
            for (int i = 0; i < str.length(); i++) {
                char string_elem = str.charAt(i);
                boolean check = Character.isWhitespace(string_elem);
                if (!check) {
                    builder_w_nums.append(string_elem);
                }
                if ((!builder_w_nums.isEmpty()) && (check || (i + 1 == str.length()))) {
                    String string_w_num = builder_w_nums.toString();
                    sum += Integer.parseInt(string_w_num);
                    builder_w_nums.delete(0, builder_w_nums.length());
                }
            }
        }
        System.out.println(sum);
    }
}