package com.pusio.day5.part2;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * https://adventofcode.com/2021/day/5
 */
public class Part2 {
    public static final int BOARD_SIZE = 10;
    public static void main(String[] args) throws IOException {

        File file = new File("src/main/resources/day5.input");
        List<String> contents = FileUtils.readLines(file, "UTF-8");
        List<Pair<Point, Point>> pairs = new ArrayList<>();

        for (String content : contents) {
            String[] splitedLine = StringUtils.split(content, " ");
            String[] splitedFirstPoint = splitedLine[0].split(",");
            Point from = new Point(Integer.valueOf(splitedFirstPoint[0]), Integer.valueOf(splitedFirstPoint[1]));

            String[] splitedSecondPoint = splitedLine[2].split(",");
            Point to = new Point(Integer.valueOf(splitedSecondPoint[0]), Integer.valueOf(splitedSecondPoint[1]));

            pairs.add(Pair.of(from,to));
        }

//        for (Pair<Point, Point> pair : pairs) {
//            System.out.println(pair.getLeft() + " " +pair.getRight());
//        }

        Table<Integer, Integer, Integer> board = HashBasedTable.create();

//        for(int x=0; x<BOARD_SIZE; x++){
//            for(int y=0; y<BOARD_SIZE; y++){
//                board.put(x,y,0);
//            }
//        }

        for (Pair<Point, Point> pair : pairs) {
            if(pair.getLeft().getX() == pair.getRight().getX()){
                int from = pair.getLeft().getY();
                int to = pair.getRight().getY();
                if(from > to){
                    int tmp = from;
                    from = to;
                    to = tmp;
                }
                for(int i = from; i<=to; i++){
                    Integer paintValue = board.get(pair.getLeft().getX(), i);
                    if(paintValue == null){
                        paintValue = 0;
                    }
                    board.put(pair.getLeft().getX(), i, paintValue+1);
                }
            }else if(pair.getLeft().getY() == pair.getRight().getY()){
                int from = pair.getLeft().getX();
                int to = pair.getRight().getX();
                if(from > to){
                    int tmp = from;
                    from = to;
                    to = tmp;
                }
                for(int i = from; i<=to; i++){
                    Integer paintValue = board.get( i, pair.getLeft().getY());
                    if(paintValue == null){
                        paintValue = 0;
                    }
                    board.put( i,pair.getLeft().getY(), paintValue+1);
                }
            }
            else{
                Point rightPoint = pair.getRight();
                Point pointer = pair.getLeft();

                Integer startValue = board.get( pointer.getX(), pointer.getY());
                if(startValue == null){
                    startValue = 0;
                }
                board.put( pointer.getX(), pointer.getY(), startValue+1);

                while(pointer.getX() != rightPoint.getX() && pointer.getY() != rightPoint.getY()){
                    if(pointer.getX() > rightPoint.getX()){
                        pointer.setX(pointer.getX()-1);
                    }else {
                        pointer.setX(pointer.getX()+1);
                    }

                    if(pointer.getY() > rightPoint.getY()){
                        pointer.setY(pointer.getY()-1);
                    }else {
                        pointer.setY(pointer.getY()+1);
                    }

                    Integer paintValue = board.get( pointer.getX(), pointer.getY());
                    if(paintValue == null){
                        paintValue = 0;
                    }
                    board.put( pointer.getX(), pointer.getY(), paintValue+1);
                }
            }
        }
        for(int y=0; y<BOARD_SIZE; y++){
            for(int x=0; x<BOARD_SIZE; x++){
                Integer integer = board.get(x, y);
                if(integer == null) {
                    System.out.print(".");
                }else{
                    System.out.print(integer);
                }

            }
            System.out.println("");
        }


        long count = board.values()
                .stream()
                .filter(a -> a >= 2)
                .count();
        System.out.println("RESULT: " + count);

    }
}
