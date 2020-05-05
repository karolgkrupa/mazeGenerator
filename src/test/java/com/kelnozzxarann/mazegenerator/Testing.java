package com.kelnozzxarann.mazegenerator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Stack;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Testing {

    @ParameterizedTest
    @MethodSource
    void isAnagram(int[] x, int top, int min) {
        MinStack obj = new MinStack();
        Arrays.stream(x).forEachOrdered(obj::push);
        obj.pop();
        int param_3 = obj.top();
        int param_4 = obj.getMin();
        assertEquals(top, param_3);
        assertEquals(min, param_4);
    }

    static Stream<Arguments> isAnagram() {
        return  Stream.of(
                arguments(new int[]{-2, 0, -3}, 0, -2)
        );
    }

    class MinStack {
        private static final int STARTING_ARR_LENGTH = 10;
        private Pair[] data;
        private int index;
        /** initialize your data structure here. */
        public MinStack() {
            data = new Pair[STARTING_ARR_LENGTH];
            index = -1;
        }

        public void push(int x) {
            if (++index>data.length) {
                data = Arrays.copyOf(data, data.length * 2);
            }
            int minElement;
            if (index < 1) {
                minElement = x;
            } else {
                minElement = Math.min(data[index-1].getMin(), x);
            }
            data[index] = new Pair(x, minElement);
        }

        public void pop() {
            index = Math.max(--index, -1);
        }

        public int top() {
            return data[index].getValue();
        }

        public int getMin() {
            return data[index].getMin();
        }
        private class Pair {
            private final int value;
            private final int minElement;

            public Pair(int value, int minElement) {
                this.value = value;
                this.minElement = minElement;
            }
            public int getValue() {
                return value;
            }
            public int getMin() {
                return minElement;
            }
        }
    }

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
}
