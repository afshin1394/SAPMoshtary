package com.saphamrah.PubFunc;

import org.junit.Test;

import static org.junit.Assert.*;

public class UnitConverterTest
{

    @Test
    public void tedadToCartonBasteAdad()
    {
        assertArrayEquals(new int[]{2,1,3} , UnitConverter.tedadToCartonBasteAdad(53, 20, 10));
        assertArrayEquals(new int[]{2,1,0} , UnitConverter.tedadToCartonBasteAdad(50, 20, 10));
        assertArrayEquals(new int[]{2,0,5} , UnitConverter.tedadToCartonBasteAdad(45, 20, 10));
        assertArrayEquals(new int[]{2,0,0} , UnitConverter.tedadToCartonBasteAdad(40, 20, 10));
        assertArrayEquals(new int[]{1,0,8} , UnitConverter.tedadToCartonBasteAdad(38, 30, 10));
        assertArrayEquals(new int[]{0,1,0} , UnitConverter.tedadToCartonBasteAdad(10, 30, 10));
        assertArrayEquals(new int[]{0,1,5} , UnitConverter.tedadToCartonBasteAdad(15, 30, 10));
        assertArrayEquals(new int[]{0,0,7} , UnitConverter.tedadToCartonBasteAdad(7, 30, 10));
        assertArrayEquals(new int[]{0,0,1} , UnitConverter.tedadToCartonBasteAdad(1, 30, 10));
        assertArrayEquals(new int[]{0,0,5} , UnitConverter.tedadToCartonBasteAdad(5, 30, 10));
        /*assertArrayEquals(new int[]{0,0,0} , UnitConverter.tedadToCartonBasteAdad(0, 30, 10, 1));
        assertArrayEquals(new int[]{0,0,0} , UnitConverter.tedadToCartonBasteAdad(10, 30, 0, 1));
        assertArrayEquals(new int[]{-10,0,0} , UnitConverter.tedadToCartonBasteAdad(-10 , 20, 10, 1));
        assertArrayEquals(new int[]{-1,0,0} , UnitConverter.tedadToCartonBasteAdad(-1 , -20, -10, -1));*/
    }


}