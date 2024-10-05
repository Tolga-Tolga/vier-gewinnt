import java.util.Arrays;

/**
 *  The game Vier Gewinnt as Java program.
 *  Copyright (C) 2024  Tolga-Tolga (Github name)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
public class PlayingField {
    // Play field defined as 0 for free space, 1 for player 1 and 2 for player 2.
    private int[][] playingField;
    private int dim_x;
    private int dim_y;

    public PlayingField(int dim_x, int dim_y) {
        super();
        try {
            this.dim_x = dim_x;
            this.dim_y = dim_y;
            playingField = new int[dim_y][dim_x];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the play field.
     */
    public void drawField() {
        for (int i = 0; i < playingField.length; i++) {
            for (int j = 0; j < playingField[i].length * 2 + 1; j++) {
                System.out.print('-');
            }
            System.out.println();
            for (int j = 0; j < playingField[i].length; j++) {
                if (j == 0) {
                    System.out.print('|');
                }
                switch (playingField[i][j]) {
                    case 0:
                        System.out.print(' ');
                        break;
                    case 1:
                        System.out.print(Config.PLAYER_1_SYMBOL);
                        break;
                    case 2:
                        System.out.print(Config.PLAYER_2_SYMBOL);
                        break;
                    default:
                        break;
                }
                System.out.print('|');
            }
            System.out.println();
        }
        for (int i = 0; i < dim_x*2 + 1; i++) {
            System.out.print('-');
        }
        System.out.println();
    }


    /**
     * @param pos    the position the stone is inserted to
     * @param player 1 for player 1, 2 for player 2
     * @return 0 for success, 1 for player is not allowed to set stone there, 2 for
     *         illegal input
     */
    public int insertStone(int pos, int player) {
        if (player > 2 || player < 0)
            return 2;
        try {
            for (int i = dim_y-1; i >= 0; i--) {
                if (playingField[i][pos] == 0) {
                    playingField[i][pos] = player;
                    return 0;
                }
            }
            return 1;

        } catch (Exception e) {
            return 2;
        }
    }

    /**
     * @param player
     * @return true if player has chain of 4
     */
    public boolean hasPlayer4Chain(int player) {
        // Check for horizontal chains
        for (int y = 0; y < dim_y; y++) {
            for (int x = 0; x <= dim_x - 4; x++) {
                if (playingField[y][x] == player && playingField[y][x + 1] == player &&
                    playingField[y][x + 2] == player && playingField[y][x + 3] == player) {
                    return true;
                }
            }
        }
    
        // Check for vertical chains
        for (int x = 0; x < dim_x; x++) {
            for (int y = 0; y <= dim_y - 4; y++) {
                if (playingField[y][x] == player && playingField[y + 1][x] == player &&
                    playingField[y + 2][x] == player && playingField[y + 3][x] == player) {
                    return true;
                }
            }
        }
    
        // Check for diagonal chains (from lower left to upper right)
        for (int y = 3; y < dim_y; y++) {
            for (int x = 0; x <= dim_x - 4; x++) {
                if (playingField[y][x] == player && playingField[y - 1][x + 1] == player &&
                    playingField[y - 2][x + 2] == player && playingField[y - 3][x + 3] == player) {
                    return true;
                }
            }
        }
    
        // Check for diagonal chains (from upper left to lower right)
        for (int y = 0; y <= dim_y - 4; y++) {
            for (int x = 0; x <= dim_x - 4; x++) {
                if (playingField[y][x] == player && playingField[y + 1][x + 1] == player &&
                    playingField[y + 2][x + 2] == player && playingField[y + 3][x + 3] == player) {
                    return true;
                }
            }
        }
    
        return false; // If no chain is found
    }    

    public boolean fieldIsFull() {
        return Arrays.stream(playingField).flatMapToInt(Arrays::stream).anyMatch(t -> t == 0);
    }
}
