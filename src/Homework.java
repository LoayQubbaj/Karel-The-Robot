import stanford.karel.SuperKarel;
import java.awt.*;
public class Homework extends SuperKarel {
    int x = 1;
    int y = 1;
    int blocked = 0;
    int steps = 0;
    int beepersUsed = 0;
    int skipAndPlace=1;
    private void reset() {
        setBeepersInBag(1000);
        blocked=0;
        steps=0;
        beepersUsed=0;
        x=1;
        y=1;
        skipAndPlace=1;
    }
    public void run() {
        reset();
        while (blocked < 2) {
            dimension();
        }
        System.out.println("Width = " + x + " Height = " + y);
        divideMap();
        System.out.println("Total number of Steps : " + steps);
        System.out.println("Total beepers used : " + beepersUsed);
    }
    private void dimension(){
        if (frontIsBlocked()) {
            turnLeft();
            blocked++;
        } else {
            move(); steps++;
            if (blocked == 0) {
                x++;
            } else{
                y++;
            }
        }
    }
    private void divideMap() {
        //Divide the map into four chambers, except when both x and y are 2, in which case, divide it into two chambers.
        if (x > 2 && y > 2 || x==2 && y==2) {
            divideIntoFour();
        }
        // Divide the map with the width of 1 into four chambers
        else if(x==1 || y==1) {
            if(x==1 && y>=8) {
                divideFirstLineIntoFour(y);
            }
        //Divide the map into less than 4 chambers if the width is 1
            else if(x==1 && y<8){
                turnLeft();
                divideLineThreeOrTwo(skipAndPlace);
            }
        // Divide the map with the height of 1 into four chambers
            else if(y==1 && x>=8){
                turnRight();
                divideFirstLineIntoFour(x);
            }
        //Divide the map with the height of 1 into less than 4 chambers because 4 chambers is not possible.
            else if(y==1 && x<8){
                divideLineThreeOrTwo(skipAndPlace);
            }
        }
        //Divide the map into 4 chambers if the width=2 and the height>=8
        else if(x==2 || y==2) {
            if(x==2 && y>=8) {
                divideFirstLineIntoFour(y);
                turnRight();
                move();
                steps++;
                turnRight();
                divideSecondLineIntoFour(y);
            }
        //Divide the map into 3 or 2 chambers when the width=2 and the height<8.
            else if(x==2 && y<8){
                turnLeft();
                divideLineThreeOrTwo(skipAndPlace);
                turnRight();
                move();steps++;
                turnRight();
                if(y!=7){
                skipAndPlace=2;
                }
                divideLineThreeOrTwo(skipAndPlace);
            }
            //Divide the map into 4 chambers if the Height=2 and the width>=8
            else if(y==2 && x>=8){
                turnRight();
                divideFirstLineIntoFour(x);
                turnLeft();
                move();
                steps++;
                turnLeft();
                divideSecondLineIntoFour(x);
            }
            //Divide the map into 3 or 2 chambers when the Height=2 and the width<8

            else if(y==2 && x<8){
                divideLineThreeOrTwo(skipAndPlace);
                turnLeft();
                move();steps++;
                turnLeft();
                if(x!=7){
                    skipAndPlace=2;
                }
                divideLineThreeOrTwo(skipAndPlace);
            }
        }
    }
//------------------------------------------------------------
    private void divideIntoFour() {
    //Divide the map diagonally when x==y
        if(x==y){
            drawDiagonal();
            if(x>2){
            turnRight();
            while(frontIsClear()){
                move(); steps++;}
            }
            if(x>2){
            turnAround();
            drawDiagonal();
            }
        }
    //Divide the map into squares when x!=y
        else{
            if(y%2==0) {
                moveToMiddle();
                drawHorizontalLine();
                turnRight();
                move(); steps++;
                turnRight();
                drawHorizontalLine();
            } else{
                moveToMiddle();
                drawHorizontalLine();
            }
            if(x%2==0) {
                moveToMiddleVertical();
                drawVerticalLine();
                turnLeft();
                move(); steps++;
                turnLeft();
                drawVerticalLine();
            } else{
                moveToMiddleVertical();
                drawVerticalLine();
            }
        }
    }
    private void divideFirstLineIntoFour(int length) {
        turnLeft();
        int k;
        while(frontIsClear()){
            if(length%4==0){
                k=length/4;
                putBeeper(); beepersUsed++;
                while(frontIsClear()) {
                    for (int i = 0; i < k; i++) {
                        if(frontIsClear()){move(); steps++;}

                    }
                    if(frontIsClear())
                    {putBeeper(); beepersUsed++;}
                }
            }
            else{
                putBeeper(); beepersUsed++;
                move(); steps++;
                length--;
            }
        }
        }
        private void divideSecondLineIntoFour(int length) {
            boolean found = false;
            int c = length;
            int answer = 0;
            int start = 1;
            while (!found) {
                if (c % 4 == 0) {
                    answer = c;
                    found = true;
                } else {
                    c--;
                }
            }
            int v;
            v = answer / 4;
            int i =1;
            while (start != answer) {
                while(i<v) {
                    if (frontIsClear()) {
                        move();
                        steps++;
                        start++;
                        i++;
                    }
                }
                i=0;
                    putBeeper(); beepersUsed++;

            }
            while (frontIsClear()) {
                move();steps++;
                putBeeper(); beepersUsed++;
            }
        }
    private void divideLineThreeOrTwo(int k) {
        if(k%2==0){
            putBeeper(); beepersUsed++;
        }
        while (frontIsClear()){
            move(); steps++;
            k++;
            if(k%2==0) {
                putBeeper(); beepersUsed++;
            }
        }
    }
    private void drawHorizontalLine() {
        while (frontIsClear()) {
            putBeeper(); beepersUsed++;
            move(); steps++;
        }
        putBeeper(); beepersUsed++;
    }
    private void drawVerticalLine() {
        while (frontIsClear()) {
            if(noBeepersPresent()){putBeeper(); beepersUsed++;}
            move(); steps++;
        }
        if(noBeepersPresent()){putBeeper(); beepersUsed++;}
        turnAround();
        while (frontIsClear()){
            if(noBeepersPresent()){putBeeper(); beepersUsed++;}
            move(); steps++;
        }
        if(noBeepersPresent()){putBeeper();beepersUsed++;}
    }
    private void drawDiagonal(){
        while (frontIsClear()) {
            if(noBeepersPresent()){putBeeper(); beepersUsed++;}
            move(); steps++;
            turnLeft();
            if (frontIsClear()) {
                move(); steps++;
                turnRight();
            }

        }
        if(noBeepersPresent()){putBeeper(); beepersUsed++;}
    }
    private void moveToMiddle() {
        turnLeft();
        for (int i = 0; i < y/2; i++) {
            move(); steps++;
        }
        turnRight();
    }
    private void moveToMiddleVertical() {
        turnAround();
        for (int i = 0; i < x/2; i++) {
            move(); steps++;
        }
        turnRight();
    }
}




