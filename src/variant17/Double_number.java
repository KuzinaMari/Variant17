package variant17;


import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.abs;


/**
 * Created by KuzinAM on 01.03.17.
 */
public class DoubleNumber {
    final ArrayList<Integer> myIntegralPart = new ArrayList<>(); //одина раз присвоили значение, больше не меняекм
    final ArrayList<Integer> myFractionalPart = new ArrayList<>();
    final Boolean sign;

    private DoubleNumber(ArrayList<Integer> integralPart, ArrayList<Integer> fractionalPart, Boolean sign ){
        myIntegralPart.addAll(integralPart);
        myFractionalPart.addAll(fractionalPart);
        this.sign = sign;
    }

    public DoubleNumber(String y) {
        ArrayList<Character> helpGetIndex = new ArrayList<>();
        Boolean sign = true;
        char[] chars = y.toCharArray();
        for (char element : chars) {
            helpGetIndex.add(element);
        }

        int index = helpGetIndex.indexOf('.');
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
                sign = false;
                helpGetIndex.remove(0);
            }
            //в массив целой части и массив дробной заносятся цифры
            if (i < index && i >= 0) {
                int intarr = Character.getNumericValue(chars[i]);
                myIntegralPart.add(intarr);
            }
            if (i > index) {
                int intarr = Character.getNumericValue(chars[i]);
                myFractionalPart.add(intarr);
            }
        }
        this.sign = sign;
    }

    public static DoubleNumber fromInt(int d) {
        return new DoubleNumber(Integer.toString(d));
    }

    public static DoubleNumber fromLong(long d) {
        return new DoubleNumber(Long.toString(d));
    }

    public static DoubleNumber fromFloat(float d) {
        return new DoubleNumber(Float.toString(d));
    }

    public static DoubleNumber fromDouble(double d) {
        return new DoubleNumber(Double.toString(d));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoubleNumber that = (DoubleNumber) o;

        if (myIntegralPart != null ? !myIntegralPart.equals(that.myIntegralPart) : that.myIntegralPart != null) return false;
        if (myFractionalPart != null ? !myFractionalPart.equals(that.myFractionalPart) : that.myFractionalPart != null) return false;
        return sign != null ? sign.equals(that.sign) : that.sign == null;
    }

    @Override
    public int hashCode() {
        int result = myIntegralPart != null ? myIntegralPart.hashCode() : 0;
        result = 31 * result + (myFractionalPart != null ? myFractionalPart.hashCode() : 0);
        result = 31 * result + (sign != null ? sign.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String sSign = " - ";
        if (sign == true) {
            sSign = " + ";
        }
        return "DoubleNumber{" +
                "myIntegralPart=" + myIntegralPart +
                ", myFractionalPart=" + myFractionalPart +
                ", sign=" +  sSign +
                '}';
    }


    //функция для установления одинаковой длины массивов двух чисел (для арифметических операций)
    private void format_for_equal_length(DoubleNumber x1, DoubleNumber x2) {
        while (x1.myIntegralPart.size() < x2.myIntegralPart.size()) {
            x1.myIntegralPart.add(0, 0);

        }
        while (x2.myIntegralPart.size() < x1.myIntegralPart.size()) {
            x2.myIntegralPart.add(0, 0);
        }
        while (x1.myFractionalPart.size() < x2.myFractionalPart.size()) {
            x1.myFractionalPart.add(0, 0);
        }
        while (x2.myFractionalPart.size() < x1.myFractionalPart.size()) {
            x2.myFractionalPart.add(0, 0);
        }
    }

    public DoubleNumber multi(DoubleNumber x2) {
        format_for_equal_length(this, x2);
        int length = this.myFractionalPart.size() + x2.myFractionalPart.size();
        String res_sign = "";
        if (((x2.sign == false) && (this.sign != false)) || ((x2.sign != false) && (this.sign == false))) {
            res_sign += "-";
        }
        double tempNumbers = 0;
        ArrayList<Integer> n1 = new ArrayList<>();
        ArrayList<Integer> n2 = new ArrayList<>();
        n1.addAll(this.myIntegralPart);
        n1.addAll(this.myFractionalPart);
        n2.addAll(x2.myIntegralPart);
        n2.addAll(x2.myFractionalPart);

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
        return new DoubleNumber(result);
    }
    private static int sumLists( ArrayList<Integer> resList, int sign1, ArrayList<Integer> list1,
                                 int sign2, ArrayList<Integer> list2, int forNextPosition ){
        int size = Math.max( list1.size(), list2.size() ); //определяется длина наибольшего масссива
        //Если имеется символ, он используется. Если нет, то он считаеется равным 0
        for( int i = size - 1; i >= 0; i-- ){
            int n1 = list1.size() > i ? list1.get( i ) : 0;
            int n2 = list2.size() > i ? list2.get( i ) : 0;
            //производится суммироваание, учитывая знак исходных чисел
            int sum = sign1 * n1 + sign2 * n2 + forNextPosition;
            resList.set( i, abs( sum ) % 10 );
            if( sum != 0 ) {
                forNextPosition = (int)((sum / abs(sum)) * Math.floor(abs(sum) / 10) % 10);
            }
        }
        return forNextPosition;
    }

    public DoubleNumber sum(DoubleNumber x2) {
        int frSize = Math.max( myFractionalPart.size(), x2.myFractionalPart.size() );
        ArrayList<Integer> newFractionalPart = new ArrayList( Collections.nCopies(frSize, 0) );
        int intSize = Math.max( myIntegralPart.size(), x2.myIntegralPart.size() );
        ArrayList<Integer> newIntegralPart = new ArrayList( Collections.nCopies(intSize, 0) );
        int forNextPos = sumLists( newFractionalPart, this.sign ? 1 : -1, myFractionalPart,
                x2.sign ? 1 : -1, x2.myFractionalPart, 0 );
        forNextPos = sumLists( newIntegralPart, this.sign ? 1 : -1, myIntegralPart,
                x2.sign ? 1 : -1, x2.myIntegralPart, forNextPos );
        if( forNextPos != 0 ) {
            newIntegralPart.add(0, abs(forNextPos));
        }
        return new DoubleNumber( newIntegralPart, newFractionalPart, Math.signum( forNextPos ) >= 0 );

    }

    private static int minusLists( ArrayList<Integer> resList, int sign1, ArrayList<Integer> list1,
                                   int sign2, ArrayList<Integer> list2, int forNextPosition ){
        int size = Math.max( list1.size(), list2.size() ); //определяется длина наибольшего масссива
        //Если имеется символ, он используется. Если нет, то он считаеется равным 0
        for( int i = size - 1; i >= 0; i-- ){
            int n1 = list1.size() > i ? list1.get( i ) : 0;
            int n2 = list2.size() > i ? list2.get( i ) : 0;
            //производится суммироваание, учитывая знак исходных чисел
            int sum = sign1 * n1 - sign2 * n2 + forNextPosition;
            resList.set( i, abs( sum ) % 10 );
            if( sum != 0 ) {
                forNextPosition = (int)((sum / abs(sum)) * Math.floor(abs(sum) / 10) % 10);
            }
        }
        return forNextPosition;
    }

    public DoubleNumber minus(DoubleNumber x2) {
        int frSize = Math.max( myFractionalPart.size(), x2.myFractionalPart.size() );
        ArrayList<Integer> newFractionalPart = new ArrayList( Collections.nCopies(frSize, 0) );
        int intSize = Math.max( myIntegralPart.size(), x2.myIntegralPart.size() );
        ArrayList<Integer> newIntegralPart = new ArrayList( Collections.nCopies(intSize, 0) );
        int forNextPos = minusLists( newFractionalPart, this.sign ? 1 : -1, myFractionalPart,
                x2.sign ? 1 : -1, x2.myFractionalPart, 0 );
        forNextPos = minusLists( newIntegralPart, this.sign ? 1 : -1, myIntegralPart,
                x2.sign ? 1 : -1, x2.myIntegralPart, forNextPos );
        if( forNextPos != 0 ) {
            newIntegralPart.add(0, abs(forNextPos));
        }
        forNextPos = -1;
        if( forNextPos == 0 ) {
            if (x2.myIntegralPart.size() == intSize && x2.sign == false && this.myIntegralPart.size() == intSize) {
                for (int i = 0; i < intSize; i++) {
                    if (x2.myIntegralPart.get(i) > this.myIntegralPart.get(i)) {
                        forNextPos = 0;
                        break;

                    }
                }
            } else {
                if (x2.sign == false ) {
                    for (int i = 0; i < intSize; i++) {
                        if (x2.myFractionalPart.get(i) > this.myFractionalPart.get(i)) {
                            forNextPos = 0;
                            break;

                        }
                    }
                }

            }
        }
        return new DoubleNumber( newIntegralPart, newFractionalPart, Math.signum( forNextPos*(-1) ) >= 0 );

    }



    public DoubleNumber round(int q) {
        ArrayList<Integer> newIntegralPart = new ArrayList<Integer>();
        ArrayList<Integer> newFractionalPart = new ArrayList<Integer>();
        newIntegralPart.addAll(myIntegralPart);
        newFractionalPart.addAll(myFractionalPart);
        if (q >= myFractionalPart.size()) {
            return new DoubleNumber( myIntegralPart, myFractionalPart, this.sign );
        } else {
            if (myFractionalPart.get(q) >= 5) {
                newFractionalPart.set(q - 1, (myFractionalPart.get(q - 1) + 1));
            }

            for (int i = q; i < newFractionalPart.size(); i++) {
                newFractionalPart.remove(i);
            }
        }
        return new DoubleNumber( newIntegralPart, newFractionalPart, this.sign );
    }

    public int toIntE() {
        if (myFractionalPart.size() != 0) {
            throw new IllegalArgumentException("Потеря точности");
        }
        int count = 0;
        int tens = 1;
        for (int i = myIntegralPart.size() - 1; i <= 0; i--) {
            count = myIntegralPart.get(i) * tens + count;
            tens = tens * 10;
        }
        return count;
    }

    public int toInt() {
        int count = 0;
        int tens = 1;
        for (int i = myIntegralPart.size() - 1; i >= 0; i--) {
            count = myIntegralPart.get(i) * tens + count;
            tens = tens * 10;
        }
        if (sign == false) {
            count = count * (-1);
        }
        return count;
    }

    public long toLongE() {
        if ((myFractionalPart.size() != 0) || abs(this.toInt()) > 3.4 * Math.pow(10, 32)) {
            throw new IllegalArgumentException("Потеря точности");
        }
        long count = 0;
        int tens = 1;
        for (int i = myIntegralPart.size() - 1; i >= 0; i--) {
            count = myIntegralPart.get(i) * tens + count;
            tens = tens * 10;
        }
        return count;
    }

    public long toLong() {
        int count = 0;
        int tens = 1;
        for (int i = myIntegralPart.size() - 1; i >= 0; i--) {
            count = myIntegralPart.get(i) * tens + count;
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
        for (int elem : myIntegralPart) {
            count = elem * tens + count;
            tens = tens * 10;
        }
        tens = 0.1f;
        for (int elem : myFractionalPart) {
            count = elem * tens + count;
            tens = tens / 10;
        }
        if (sign == false) {
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
        double tens = Math.pow(10, myIntegralPart.size() - 1);
        for (int elem : myIntegralPart) {
            count = elem * tens + count;
            tens = tens / 10;
        }
        for (int elem : myFractionalPart) {
            count = elem * tens + count;
            tens = tens / 10;
        }
        if (sign == false) {
            count = count * (-1);
        }
        return count;
    }


}

