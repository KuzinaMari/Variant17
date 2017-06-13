package variant17;
import java.util.Arrays;

public class DecimalNumber{
    private static final char MARK = '.';//константа точка, нужна для сравнения с символами
    private byte mySign;//здесь хранится знак числа, byte потому что так удобнее использовать в арифметических операциях
    private byte[] myMantissa; //хранятся цифры числа
    private int myExponent;//здесь хренится степень числа(кол-во цифр после запятой)
    public DecimalNumber( String number ){
        final int len = number.length();//длина числа(константа)
        myMantissa = new byte[ len ];//создается массив размером с длину числа
        myExponent = 0;//обнуляется степень
        mySign = 1;
        int mantissaIndex = -1;//хранится какой конкретно бит числа мы заполняем
        boolean integralPart = true;//числа до точки
        int offset = 0;//будет считать нули на конце
        for( int i = 0; i < len; i++ ){
            myMantissa[ i ] = -1;//отладочное значение
            char chr = number.charAt( i );//берем очередной символ
            if( Character.isDigit( chr ) ){//если он цифра
                byte digit = ( byte ) Character.getNumericValue( chr );//переводим char в byte
                if( digit > 0 || mantissaIndex >= 0 ){ //если в начале нет значащих цифр, то они здесь отбросятся
                    mantissaIndex++;
                    myMantissa[ mantissaIndex ] = digit;//заполняем число
                }
                if( integralPart ){//если не было точки
                    myExponent = mantissaIndex;//записываем индекс, чтобы при умножении на мантиссу получить число
                }else if( digit == 0 && mantissaIndex < 0 ){//если точка была, или нам важно кол-во нулей
                    myExponent--;
                }
                offset = digit == 0 ? offset + 1 : 0;//если офсет считал нули в середине, то мы его сбросим
            }else if( chr == MARK ){
                integralPart = false;
            }else if( chr == '-' && mantissaIndex < 0 ){//если минус стоит в начале, то знак будет -1
                mySign = -1;
            }
            else throw new RuntimeException("Неверный форма строки");
        }
        if( mantissaIndex < 0 ){//не оказалось цифр, или число 0
            myMantissa = new byte[ 1 ];//массив из одного элемента(0)
            return;
        }
        byte[] newMantissa = new byte[ mantissaIndex + 1 - offset ];//если вдруг лишние элементы, (-1), то мы убираем их
        for( int i = 0; i <= mantissaIndex - offset; i++ ) newMantissa[ i ] = myMantissa[ i ];
        myMantissa = newMantissa;
        myExponent = myExponent - mantissaIndex + offset;//кол-во цифр в дробной части и кол-во нулей на конце
    }
    private DecimalNumber( int sign, byte[] mantissa, int exponent ){//функция для кнутреннего использования, чтобы создавать каждый раз новое число, а первоначальное не поменялось
        mySign = (byte) sign;
        myMantissa = new byte[ mantissa.length ];
        myExponent = exponent;
        int mantissaIndex = -1;
        int offset = 0;
        for( int i = 0; i < myMantissa.length; i++ ){
            myMantissa[ i ] = -1;
            byte digit = ( byte ) mantissa[ i ];
            if( digit > 0 || mantissaIndex >= 0 ){ //убираются нули в начале
                mantissaIndex++;
                myMantissa[ mantissaIndex ] = digit;
            }
            offset = digit == 0 ? offset + 1 : 0;
        }
        if( mantissaIndex < 0 ){
            myMantissa = new byte[ 1 ];
            myExponent = 0;
            return;
        }
        if( mantissaIndex < myMantissa.length - 1 || offset > 0 ){//если не заполнили новую мантиссу
            byte[] newMantissa = new byte[ mantissaIndex + 1 - offset ];
            System.arraycopy( myMantissa, 0, newMantissa, 0, mantissaIndex + 1 - offset );//обрезали массив, если конец пустой
            myMantissa = newMantissa;
            myExponent = myExponent < 0 ? myExponent : myExponent + myMantissa.length - 1 - mantissaIndex + offset;
        }
    }
    static public DecimalNumber fromInteger( int number ){
        return new DecimalNumber( Integer.toString( number ) );
    }
    static public DecimalNumber fromLong( long number ){
        return new DecimalNumber( Long.toString( number ) );
    }
    static public DecimalNumber fromFloat( float number ){
        return new DecimalNumber( Float.toString( number ) );
    }
    static public DecimalNumber fromDouble( double number ){
        return new DecimalNumber( Double.toString( number ) );
    }
    private int integralSize(){//вычислиление длины целой части
        return myMantissa.length + myExponent;
    }
    private int fractionalSize(){//выычисленеи длины дробной части
        return - myExponent;
    }
    private int mantissaLength(){//длинна мантиссы
        return myMantissa.length;
    }
    private static byte getNthDigit( int number, int n ){//получение nой цифры числа
        return (byte) ( ( number / Math.pow( 10, n - 1) ) % 10 );
    }
    private static byte[] prepend( byte[] array, byte digit ){//добавляет массива вперед цифру
        byte[] res = new byte[ array.length + 1 ];
        res[ 0 ] = digit;
        System.arraycopy( array, 0, res, 1, array.length );
        return res;
    }
    private DecimalNumber sum( int sign, DecimalNumber number ){//операция для суммирования или вычитания
        final int newIntegralSize = Math.max( integralSize(), number.integralSize() );//максимум между числами по размеру целых частей
        final int newFractionalSize = Math.max( fractionalSize(), number.fractionalSize() );//максимум между числами по размеру дробных частей
        final int newLen = newIntegralSize + newFractionalSize;//длина массива,  который нужен для суммы
        byte[] res = new byte[ newLen ];//создаем массив
        int forNextPosition = 0;
        int sum = 0;
        for( int i = newLen - 1; i >= 0; i-- ){//идем с конца
            int index = i - ( newIntegralSize - integralSize() );//вычисление индекса цифры(так как числа могут быть разной длины)
            byte digit1 = index < mantissaLength() && index >= 0 ? myMantissa[ index ] : 0;//если нет нужного разряда сложение будет с 0
            index = i - ( newIntegralSize - number.integralSize() );
            byte digit2 = index < number.mantissaLength() && index >= 0 ? number.myMantissa[ index ] : 0;
            sum = mySign * digit1 + sign * number.mySign * digit2 + Integer.signum( sum ) * forNextPosition;//сложение чифр с учетом переноса знака
            byte digit = getNthDigit( Math.abs( sum ), 1 );//последнюю цифру из полученной суммы записываем, остальное в перенос
            forNextPosition = getNthDigit( Math.abs( sum ), 2 );
            res[ i ] = digit;

        }
        if( forNextPosition != 0 ){//если суммирование окончено, а перенос остался, то его записываем вперед массива
            res = prepend( res, ( byte ) forNextPosition );
        }
        return new DecimalNumber( ( sum >= 0 ? 1 : -1 ), res, -newFractionalSize );
    }
    private static byte[] digitProduct( byte[] mantissa, byte digit ){//умножение на цифру(не число!)
        byte[] res = new byte[ mantissa.length ];
        int forNextPosition = 0;
        for( int i = mantissa.length - 1; i >=0; i-- ){
            int product = mantissa[ i ] * digit + forNextPosition;
            res[ i ] = getNthDigit( product, 1 );
            forNextPosition = getNthDigit( product, 2 );
        }
        if( forNextPosition != 0 ){
            res = prepend( res, ( byte ) forNextPosition );
        }
        return res;
    }
    public DecimalNumber product( DecimalNumber number ){//умножение на число
        DecimalNumber res = new DecimalNumber( "0" );
        for( int i = number.myMantissa.length - 1; i >=0; i-- ){
            byte digit = number.myMantissa[ i ];//извлекаем число, на которое умножим
            byte[] digitProduct = digitProduct( myMantissa, digit );//умножение на число
            DecimalNumber numb = new DecimalNumber( mySign * number.mySign, digitProduct
                    , myExponent + number.myExponent + ( number.myMantissa.length -1 - i ) );//создаем новое число
            res = res.sum( numb );
        }
        return res;
    }
    public DecimalNumber sum( DecimalNumber number ){//само суммирование
        return sum( 1, number );
    }
    public DecimalNumber minus( DecimalNumber number ){//вычитание
        return sum( -1, number );
    }
    public String toString( boolean debug ){
        StringBuilder res = new StringBuilder( mySign < 0 ? "-" : "" );
        if( integralSize() <= 0 ) res.append( "0"+MARK );
        for( int i = 0; i < - myExponent - myMantissa.length; i++ ) res.append( "0" );
        int index = -1;
        for( byte digit : myMantissa ){
            if( digit < 0 || digit > 9 ) continue;
            index++;
            if( index == integralSize() && index > 0 ){
                res.append( MARK );
            }
            res.append( digit );
        }
        for( int i = 0; i < myExponent; i++ ) res.append( "0" );
        if( debug ) {
            res.append(" ");
            res.append(Arrays.toString(myMantissa));
            res.append(" ");
            res.append(myExponent);
        }
        return res.toString();
    }
    @Override
    public String toString(){
        return toString( false );
    }
    private static long arrayToLong( byte[] mantissa ){
        long res = 0;
        for( int i = mantissa.length - 1; i >= 0; i-- ){
            res += mantissa[ i ] * Math.pow( 10, mantissa.length - 1 - i );
        }
        return res;
    }

    public double toDouble(){
        return arrayToLong( myMantissa ) * Math.pow( 10, myExponent );
    }

    public float toFloat(){
        return (float) toDouble();
    }

    public long toLong(){
        if( myMantissa.length + myExponent <= 0 ){
            throw new RuntimeException( "Дробная часть" );
        }
        return Math.round( toDouble() );
    }

    public int toInteger(){
        return (int) toLong();
    }

    public DecimalNumber round( int precise ){
        int len = myMantissa.length;
        int index = len + myExponent + precise;
        if( index <= 0 ){
            return new DecimalNumber( "0" );
        }
//        byte first = myMantissa[  ];
        byte[] newMantissa = new byte[ index ];
        byte forNextPosition = -1;
        byte digit = myMantissa[ index ];
        if( digit < 5 ){
            forNextPosition = 0;
        }else if( digit > 5 ){
            forNextPosition = 1;
        }else{
            if( index + 1 >= len ){ //нету значащих после отбрасываемой
                byte lastSavedDigit = myMantissa[ index - 1 ];
                if( lastSavedDigit % 2 == 0 ) {
                    forNextPosition = 0;
                }else{
                    forNextPosition = 1;
                }
            }else{
                forNextPosition = 1;
            }
//            byte nextDigit = ( index + 1 < len ) ? myMantissa[ index + 1 ] : 0;
//            if( nextDigit  )
        }
        int offset = 0;
        int mantissaIndex = -1;
        for( int i = index - 1; i >= 0; i-- ){
            digit = myMantissa[ i ];
            byte newDigit = (byte) ( digit + mySign * forNextPosition );
            if( newDigit == 0 ){
                if( mantissaIndex < 0 ){//ещё нет записи в новую мантиссу
                    offset += 1;
                }else{
                    offset = 0;
                    mantissaIndex++;
                    if( newDigit < 10 ){
                        newMantissa[ i ] = newDigit;
                        forNextPosition = 0;
                    }else{
                        newMantissa[ i ] = 0;
                        forNextPosition = 1;
                    }
                }
            }else{
                mantissaIndex++;
                if( newDigit < 10 ){
                    newMantissa[ i ] = newDigit;
                    forNextPosition = 0;
                }else{
                    newMantissa[ i ] = 0;
                    forNextPosition = 1;
                }
            }
        }
        byte[] newMantissa2 = newMantissa;
        if( offset > 0 ){
            newMantissa2 = new byte[ index - offset ];
            System.arraycopy( newMantissa, 0, newMantissa2, 0, index - offset );
        }
        return new DecimalNumber( mySign, newMantissa2, - precise + offset );
    }
    public boolean equals( DecimalNumber number ){
        return Arrays.equals( myMantissa, number.myMantissa ) && myExponent == number.myExponent && mySign == number.mySign;
    }

}
