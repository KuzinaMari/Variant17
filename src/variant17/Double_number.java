package variant17;


import java.util.ArrayList;



import static java.lang.Math.abs;


/**
 * Created by KuzinAM on 01.03.17.
 */
public class Double_number {
    ArrayList<Integer> arr_int = new ArrayList<>();
    ArrayList<Integer> arr_dob = new ArrayList<>();
    String sign;

    public Double_number(String y) {
        ArrayList<Integer> arr_int = new ArrayList<>();
        ArrayList<Integer> arr_dob = new ArrayList<>();
        ArrayList<Character> help_get_index = new ArrayList<>();
        String sign = "";
        char[] chars = y.toCharArray();
        for (char element : chars) {
            help_get_index.add(element);
        }
        int index = help_get_index.indexOf('.');
        for (int i = 0; i < chars.length; i++) {
            //проверка формата строки (на единственную запятую и наличие исключительно цифр в строке)
            try {
                if (i != index) {
                    int elem = Character.getNumericValue(chars[i]);
                }
            } catch (NumberFormatException e) {
                System.err.println(" Неверный формат строки! ");
            }
            //устанавливается значения знака
            if (chars[0] == '-') {
                sign = "-";
                help_get_index.remove(0);
            }
            //в массив целой части и массив дробной заносятся цифры
            if (i < index && i >= 0) {
                int intarr = Character.getNumericValue(chars[i]);
                arr_int.add(intarr);
            }
            if (i > index) {
                int intarr = Character.getNumericValue(chars[i]);
                arr_dob.add(intarr);
            }
        }
        this.arr_dob = arr_dob;
        this.arr_int = arr_int;
        this.sign = sign;
    }

    public static Double_number fromInt(int d) {
        return new Double_number(Integer.toString(d));
    }

    public static Double_number fromLong(long d) {
        return new Double_number(Long.toString(d));
    }

    public static Double_number fromFloat(float d) {
        return new Double_number(Float.toString(d));
    }

    public static Double_number fromDouble(double d) {
        return new Double_number(Double.toString(d));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Double_number that = (Double_number) o;

        if (arr_int != null ? !arr_int.equals(that.arr_int) : that.arr_int != null) return false;
        if (arr_dob != null ? !arr_dob.equals(that.arr_dob) : that.arr_dob != null) return false;
        return sign != null ? sign.equals(that.sign) : that.sign == null;
    }

    @Override
    public int hashCode() {
        int result = arr_int != null ? arr_int.hashCode() : 0;
        result = 31 * result + (arr_dob != null ? arr_dob.hashCode() : 0);
        result = 31 * result + (sign != null ? sign.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String sSign = "";
        if (sign == "") {
            sSign = " + ";
        }
        return "Double_number{" +
                "arr_int=" + arr_int +
                ", arr_dob=" + arr_dob +
                ", sign=" + sSign +
                '}';
    }


    //функция для установления одинаковой длины массивов двух чисел (для арифметических операций)
    private void format_for_equal_length(Double_number x1, Double_number x2) {
        while (x1.arr_int.size() < x2.arr_int.size()) {
            x1.arr_int.add(0, 0);

        }
        while (x2.arr_int.size() < x1.arr_int.size()) {
            x2.arr_int.add(0, 0);
        }
        while (x1.arr_dob.size() < x2.arr_dob.size()) {
            x1.arr_dob.add(0, 0);
        }
        while (x2.arr_dob.size() < x1.arr_dob.size()) {
            x2.arr_dob.add(0, 0);
        }
    }

    public Double_number multi(Double_number x2) {
        format_for_equal_length(this, x2);
        int length = this.arr_dob.size() + x2.arr_dob.size();
        String res_sign = "";
        if (((x2.sign == "-") && (this.sign != "-")) || ((x2.sign != "-") && (this.sign == "-"))) {
            res_sign += "-";
        }
        double tempNumbers = 0;
        ArrayList<Integer> n1 = new ArrayList<>();
        ArrayList<Integer> n2 = new ArrayList<>();
        n1.addAll(this.arr_int);
        n1.addAll(this.arr_dob);
        n2.addAll(x2.arr_int);
        n2.addAll(x2.arr_dob);

        int tens = 1;
// идем с конца второго числа и до его начала
        for (int i = n1.size() - 1; i >= 0; i--) {
            int head = 0; // если число, полученное от умножения, больше 9 - сохраняем перенос на разряд влево
            int currentNumber; // текущие число, на которое производится умножение всего первого числа
            int n; // здесь будет храниться результат умножения
            currentNumber = n1.get(i); // записываем текущую цифру, которую будем умножать на первое число
            String intermediateNumber = ""; // здесь будет наполняться промежуточное число
            for (int j = n2.size() - 1; j >= 0; j--) { // начинаем перебор первого числа
                n = (currentNumber * n2.get(j)) + head; // производим умножение
                head = 0;
                while (n >= 10) {
                    n = n - 10;
                    head++;
                }
// добавляем в начало нашего промежутчного числа
                intermediateNumber = String.valueOf(n) + intermediateNumber;
            }
            if (head != 0) {
                intermediateNumber = String.valueOf(head) + intermediateNumber;
            }
            tempNumbers = Integer.parseInt(intermediateNumber) * tens + tempNumbers;
            tens = tens * 10;
        }
        tempNumbers = tempNumbers / Math.pow(10, length);
        String result = res_sign + tempNumbers;
        return new Double_number(result);
    }

    public Double_number sum(Double_number x2) {
        format_for_equal_length(this, x2);
        //переводим знак числа в int (-1 или 1)
        int signX2 = 1;
        int signThis = 1;
        if (x2.sign == "-") {
            signX2 = -1;
        }
        if (this.sign == "-") {
            signThis = -1;
        }
        int count_dob = 0; //здесь будет сумма для дробной части
        int count = 0; // здесь будет сумма для целой части
        String res_sign = "";
        int head = 0; //здесь хранится перенос разряда
        int tens = 1;
        //для дробной части
        for (int i = this.arr_dob.size() - 1; i >= 0; i--) {

            int a = signX2 * x2.arr_dob.get(i) + signThis * this.arr_dob.get(i) + head;
            head = 0;
            while (a >= 10) {
                a = a - 10;
                head++;
            }
            count_dob = count_dob + a * tens;
            tens = tens * 10;
        }
        tens = 1;
        //для целой части
        for (int i = this.arr_int.size() - 1; i >= 0; i--) {

            int a = signX2 * x2.arr_int.get(i) + signThis * this.arr_int.get(i) + head;
            head = 0;
            while (a >= 10) {
                a = a - 10;
                head++;
            }
            count = count + a * tens;
            tens = tens * 10;
        }
        if (count < 0) {
            res_sign = "-";
            count_dob = count_dob * (-1);
            count = count * (-1);
        }
        if (head != 0) {
            head = head * tens;
            count = count + head;
        }
        String result = res_sign + count + "." + count_dob;
        return new Double_number(result);
    }

    public Double_number minus(Double_number x2) {
        format_for_equal_length(this, x2);
        //переводим знак числа в int (-1 или 1)
        int signX2 = 1;
        int signThis = 1;
        if (x2.sign == "-") {
            signX2 = -1;
        }
        if (this.sign == "-") {
            signThis = -1;
        }
        int count_dob = 0; //здесь будет сумма для дробной части
        int count = 0; // здесь будет сумма для целой части
        String res_sign = "";
        int head = 0; //здесь хранится перенос разряда
        int tens = 1;
        //для дробной части
        for (int i = this.arr_dob.size() - 1; i >= 0; i--) {

            int a = signThis * this.arr_dob.get(i) - signX2 * x2.arr_dob.get(i) + head;
            head = 0;
            while (a >= 10) {
                a = a - 10;
                head++;
            }
            count_dob = count_dob + a * tens;
            tens = tens * 10;
        }
        tens = 1;
        //для целой части
        for (int i = this.arr_int.size() - 1; i >= 0; i--) {

            int a = signThis * this.arr_int.get(i) - signX2 * x2.arr_int.get(i) + head;
            head = 0;
            while (a >= 10) {
                a = a - 10;
                head++;
            }
            count = count + a * tens;
            tens = tens * 10;
        }
        if (count < 0 || count_dob < 0) {
            res_sign = "-";
            count_dob = count_dob * (-1);
            count = count * (-1);
        }
        if (head != 0) {
            head = head * tens;
            count = count + head;
        }
        String result = res_sign + count + "." + count_dob;
        return new Double_number(result);
    }


    public Double_number curcle(int q) {
        if (q >= arr_dob.size()) {
            return this;
        } else {
            if (arr_dob.get(q) >= 5) {
                arr_dob.set(q - 1, (arr_dob.get(q - 1) + 1));
            }

            for (int i = q; i < arr_dob.size(); i++) {
                arr_dob.remove(i);
            }
        }
        return this;
    }

    public int toIntE() {
        if (arr_dob.size() != 0) {
            throw new IllegalArgumentException("Потеря точности");
        }
        int count = 0;
        int tens = 1;
        for (int i = arr_int.size() - 1; i <= 0; i--) {
            count = arr_int.get(i) * tens + count;
            tens = tens * 10;
        }
        return count;
    }

    public int toInt() {
        int count = 0;
        int tens = 1;
        for (int i = arr_int.size() - 1; i >= 0; i--) {
            count = arr_int.get(i) * tens + count;
            tens = tens * 10;
        }
        if (sign == "-") {
            count = count * (-1);
        }
        return count;
    }

    public long toLongE() {
        if ((arr_dob.size() != 0) || abs(this.toInt()) > 3.4 * Math.pow(10, 32)) {
            throw new IllegalArgumentException("Потеря точности");
        }
        long count = 0;
        int tens = 1;
        for (int i = arr_int.size() - 1; i >= 0; i--) {
            count = arr_int.get(i) * tens + count;
            tens = tens * 10;
        }
        return count;
    }

    public long toLong() {
        int count = 0;
        int tens = 1;
        for (int i = arr_int.size() - 1; i >= 0; i--) {
            count = arr_int.get(i) * tens + count;
            tens = tens * 10;
        }
        return count;
    }

    public float toFloatE() {
        if (this.toFloat() > 3.4 * Math.pow(10, 32)) {
            throw new IllegalArgumentException("Потеря точности");
        }
        return this.toFloat();
    }

    public float toFloat() {
        float count = 0;
        float tens = 1;
        for (int elem : arr_int) {
            count = elem * tens + count;
            tens = tens * 10;
        }
        tens = 0.1f;
        for (int elem : arr_dob) {
            count = elem * tens + count;
            tens = tens / 10;
        }
        if (sign == "-") {
            count = count * (-1);
        }
        return count;
    }

    public double toDoubleE() {
        if (abs(this.toDouble()) > 1.7 * Math.pow(10,
                308))
            throw new IllegalArgumentException("Потеря точности");
        return this.toDouble();
    }

    public double toDouble() {
        double count = 0;
        double tens = Math.pow(10, arr_int.size() - 1);
        for (int elem : arr_int) {
            count = elem * tens + count;
            tens = tens / 10;
        }
        for (int elem : arr_dob) {
            count = elem * tens + count;
            tens = tens / 10;
        }
        if (sign == "-") {
            count = count * (-1);
        }
        return count;
    }

}

