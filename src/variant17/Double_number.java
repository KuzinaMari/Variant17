package variant17;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;


import static java.lang.Math.abs;
import static java.lang.String.valueOf;

/**
 * Created by KuzinAM on 01.03.17.
 */
public class Double_number {
    ArrayList<Integer> arr =new ArrayList<>();
    private String arr2 = new String();

    public Double_number(String y) {
        arr2 = y;
        ArrayList<Integer> arr = new ArrayList<>();
        try {
            Double d = new Double(y);
        } catch (NumberFormatException e) {
            System.err.println(" Неверный формат строки! ");
        }
        char[] chars = y.toCharArray();
        if (chars[0] == '-') {
            arr.add(0, -1);
        } else {
            arr.add(0, 1);
        }

        for (int i = 0; i < chars.length; i++) {

            if (chars[i] == '.') {
                arr.add(10);
            } else {
                int intarr = Character.getNumericValue(chars[i]);
                arr.add(intarr);
            }
        }
        if (chars[0] == '-') {
            arr.remove(1);
        }

// System.out.print(arr);
        this.arr = arr;
    }

    public Double_number fromInt(int d) {
        return new Double_number(Integer.toString(d));
    }

    public Double_number fromLong(long d) {
        return new Double_number(Long.toString(d));
    }

    public Double_number fromFloat(float d) {
        return new Double_number(Float.toString(d));
    }

    public Double_number fromDouble(double d) {
        return new Double_number(Double.toString(d));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Double_number that = (Double_number) o;

        return arr != null ? arr.equals(that.arr) : that.arr == null;
    }

    @Override
    public int hashCode() {
        return arr != null ? arr.hashCode() : 0;
    }

    public ArrayList<Integer> count(Double_number x) {
        ArrayList<Integer> c = new ArrayList<>();
        c.add(x.arr.size() - x.arr.indexOf(10) - 1);
        c.add(x.arr.size() - c.get(0) - 2);
        return c;
    }

    @Override
    public String toString() {
        String x = "";
// System.out.print(arr);
        if (arr.get(0) == -1) {
            x += "-";
        }
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i) != 10) x += arr.get(i);
            else x += ".";
        }
        return "Число = " + x +
                ", кол-во знаков в целой части = " + count(this).get(1) +
                ", кол-во знаков в дробной части = " + count(this).get(0);
    }


    public void format(Double_number x1, Double_number x2) {
        int index1 = x1.arr.indexOf((Integer) 10);
        int index2 = x2.arr.indexOf((Integer) 10);
        while (index1 < index2) {
            x1.arr.add(1, 0);
            index1++;
        }
        while (index2 < index1) {
            x2.arr.add(1, 0);
            index2++;
        }
        index1 = x1.arr.indexOf(10);
        index2 = x2.arr.indexOf(10);

        while (x1.arr.size() - index1 > x2.arr.size() - index2) {
            x2.arr.add(0);
        }
        while (x2.arr.size() - index2 > x1.arr.size() - index1) {
            x2.arr.add(0);
        }
    }

    private boolean isComma(int code) {
        return code == 10;
    }


    private ArrayList formInt(int i, int q) {
        String numbers = Integer.toString(i);
        ArrayList<Integer> result = new ArrayList<>();
        for (int j = 0; j < numbers.length(); j++) {
            result.add(0, (Integer.parseInt("" + numbers.charAt(j))));
        }
        while (result.size() < q) {
            result.add(0, 0);
        }
        return result;
    }

    public String multi(Double_number x2) {
        int z_res = this.arr.get(0) * x2.arr.get(0);
        format(this, x2);
        if (this.arr.get(0) == -1) {
            System.out.print("-");
        } else {
            System.out.print(" ");
        }
        for (int i = 1; i < this.arr.size(); i++) {
            if (this.arr.get(i) != 10) {
                System.out.print(this.arr.get(i));
            } else {
                System.out.print(".");
            }
        }
        System.out.println("");
        System.out.println("*");
        if (x2.arr.get(0) == -1) {
            System.out.print("-");
        } else {
            System.out.print(" ");
        }
        for (int i = 1; i < x2.arr.size(); i++) {
            if (x2.arr.get(i) != 10) {
                System.out.print(x2.arr.get(i));
            } else {
                System.out.print(".");
            }
        }
        System.out.println("");
        for (Integer element : x2.arr) {
            System.out.print("_");
        }

        ArrayList<Double> tempNumbers = new ArrayList<>();
        double tens = 1;
        double count = 0;
        int currentNumber;
        int n;
        int head = 0;
        int rest;
        for (int i = x2.arr.size() - 1; i > 0; i--) {
            if (x2.arr.get(i) != 10) {
                currentNumber = x2.arr.get(i);
                String intermediateNumber = "";
                for (int j = this.arr.size() - 1; j > 0; j--) {
                    if (this.arr.get(j) != 10) {
                        n = (currentNumber * this.arr.get(j)) + head;
                        head = 0;
                        if (n > 9 && j != 1) {
                            while (n > 9) {
                                n = n - 10;
                                head++;
                            }
                        }
                        rest = n;
                        intermediateNumber = String.valueOf(rest) + intermediateNumber;
                    }

                }
                tempNumbers.add((Integer.parseInt(intermediateNumber)) * tens);
                count = count + Integer.parseInt(intermediateNumber) * tens;
                tens = tens * 10;
            }
        }
        System.out.println("");
        for (int i = 0; i < tempNumbers.size(); i++) {
            if (i > 0 && i != tempNumbers.size()) {
                System.out.println("+");
            }
            System.out.println(tempNumbers.get(i));
        }
        for (Integer element : x2.arr) {
            System.out.print("_");
        }
        System.out.println("");
        if (z_res == -1) {
            System.out.print("-");
        }
        System.out.println(count / Math.pow(10, count(this).get(0) + count(x2).get(0)));
        return null;
    }

    public void sum(Double_number x2) {
        format(this, x2);
        if (this.arr.get(0) == -1) {
            System.out.print("-");
        } else {
            System.out.print(" ");
        }
        for (int i = 1; i < this.arr.size(); i++) {
            if (this.arr.get(i) != 10) {
                System.out.print(this.arr.get(i));
            } else {
                System.out.print(",");
            }
        }
        System.out.println("");
        System.out.println("+");
        if (x2.arr.get(0) == -1) {
            System.out.print("-");
        } else {
            System.out.print(" ");
        }
        for (int i = 1; i < x2.arr.size(); i++) {
            if (x2.arr.get(i) != 10) {
                System.out.print(x2.arr.get(i));
            } else {
                System.out.print(",");
            }
        }
        double max = Math.max(abs(this.toDouble()), abs(x2.toDouble()));
        double count = 0.0;
        double tens = 1 / Math.pow(10, (count(this).get(0)));
        int k = 0;
        for (int i = this.arr.size() - 1; i > 0; i--) {
            if (i != x2.arr.indexOf(10)) {
                int z;
                if (x2.arr.get(0) * x2.arr.get(i) + this.arr.get(0) * this.arr.get(i) > 0) {
                    z = 1;
                } else {
                    z = -1;
                }
                int a = x2.arr.get(0) * x2.arr.get(i) + this.arr.get(0) * this.arr.get(i) + k * (z);
                k = 0;
                while (a > 10) {
                    a = a - 10;
                    k++;
                }
                count = count + a * tens;
                tens = tens * 10;
            }
        }
        count = count + k * tens;
        if (max < 0) {
            count = count * (-1);
        }
        System.out.println("");
        for (Integer elem : this.arr) {
            System.out.print("_");
        }
        System.out.println("");
        if (count > 0) {
            System.out.print(" " + count);
        } else {
            System.out.print(count);
        }
    }


    public void minus(Double_number x2) {
        format(this, x2);
        if (this.arr.get(0) == -1) {
            System.out.print("-");
        } else {
            System.out.print(" ");
        }
        for (int i = 1; i < this.arr.size(); i++) {
            if (this.arr.get(i) != 10) {
                System.out.print(this.arr.get(i));
            } else {
                System.out.print(",");
            }
        }
        System.out.println("");
        System.out.println("-");
        if (x2.arr.get(0) == -1) {
            System.out.print("-");
        } else {
            System.out.print(" ");
        }
        for (int i = 1; i < x2.arr.size(); i++) {
            if (x2.arr.get(i) != 10) {
                System.out.print(x2.arr.get(i));
            } else {
                System.out.print(",");
            }
        }
        double max = Math.max(abs(this.toDouble()), abs(x2.toDouble()));
        double count = 0.0;
        double tens = 1 / Math.pow(10, (count(this).get(0)));
        int k = 0;
        for (int i = this.arr.size() - 1; i > 0; i--) {
            if (i != x2.arr.indexOf(10)) {
                int z;
                if (x2.arr.get(0) * x2.arr.get(i) - this.arr.get(0) * this.arr.get(i) > 0) {
                    z = 1;
                } else {
                    z = -1;
                }
                int a = x2.arr.get(0) * x2.arr.get(i) - this.arr.get(0) * this.arr.get(i) + k * (z);
                k = 0;
                while (a > 10) {
                    a = a - 10;
                    k++;
                }
                count = count + a * tens;
                tens = tens * 10;
            }
        }
        count = count + k * tens;
        if (max > 0) {
            count = count * (-1);
        }
        System.out.println("");
        for (Integer elem : this.arr) {
            System.out.print("_");
        }
        System.out.println("");
        if (count > 0) {
            System.out.print(" " + count);
        } else {
            System.out.print(count);
        }
    }

    public Double_number curcle(int q) {
        int index = this.arr.indexOf(10);
        if (arr.get(index + q + 1) >= 5) {
            arr.set(index + q, (arr.get(index + q) + 1));
        }
        for (int i = index + q + 1; i < arr.size(); i++) {
            arr.remove(i);
        }
        String s = "";
        if (arr.get(0) == -1) s += "-";
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i) != 10) s += arr.get(i);
            else s += ".";
        }
        return new Double_number(s);
    }

    public int toIntE() {
        if ((int) Double.parseDouble(arr2) != Double.parseDouble(arr2))
            throw new IllegalArgumentException("Потеря точности");
        return (int) Double.parseDouble(arr2);
    }

    public int toInt() {
        return (int) Double.parseDouble(arr2);
    }

    public long toLongE() {
        if ((long) Double.parseDouble(arr2) != Double.parseDouble(arr2))
            throw new IllegalArgumentException("Потеря точности");
        return (long) Double.parseDouble(arr2);
    }

    public long toLong() {
        return (long) Double.parseDouble(arr2);
    }

    public float toFloatE() {
        if (abs(Double.parseDouble(arr2)) > 3.4 * Math.pow(10, 32))
            throw new IllegalArgumentException("Потеря точности");
        return (float) Double.parseDouble(arr2);
    }

    public float toFloat() {
        return (float) Double.parseDouble(arr2);
    }

    public double toDoubleE() {
        if (abs(Double.parseDouble(arr2)) > 1.7 * Math.pow(10,
                308))
            throw new IllegalArgumentException("Потеря точности");
        return Double.parseDouble(arr2);
    }

    public double toDouble() {
        return Double.parseDouble(arr2);
    }
}

