package tictactoe;

import java.util.Scanner;

public class Main {

    public enum Status {

        X("X wins"),
        O("O wins"),
        DRAW("Draw"),
        NOT_FINISHED("Game not finished"),
        IMPOSSIBLE("Impossible"),
        NOTHING("nothing");

        String label;

        Status(String label) {
            this.label = label;
        }

        static String getLabel(Status status) {
            return status.label;
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[][] xadrex = new String[3][3];

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                xadrex[x][y] = " ";
            }
        }

        showCamp(xadrex);


        boolean vitoria = false;
        int tentativa = 1;
        while (!vitoria) {
            System.out.println("Enter the Coordinates");
            try {
                String oneString = scanner.next();
                String twoString = scanner.next();

                int one = Integer.parseInt(oneString);
                int two = Integer.parseInt(twoString);

                if (one > 3 || two > 3 || one < 1 || two < 0) {
                    System.out.println("Coordinates should be from 1 to 3!");
                } else {
                    int col = col(one);
                    int line = line(two);
                    if (xadrex[line][col].equals(" ")) {
                        if (tentativa % 2 == 0) {
                            xadrex[line][col] = "O";
                        } else {
                            xadrex[line][col] = "X";
                        }
                        tentativa++;
                        showCamp(xadrex);
                        vitoria = verificaValores(xadrex);
                        boolean status = verifyStatusGame(xadrex);
                        if (!status && !vitoria) {
                            System.out.println(Status.DRAW);
                            break;
                        }
                    } else {
                        System.out.println("This cell is occupied! Choose another one!");
                    }
                }
            } catch (Exception e) {
                System.out.println("You shold enter numbers!");
            }
        }
    }

    private static boolean verificaValores(String[][] tabuleiro) {
        String ganhador = null;
        Status statusLinha = verificaLinha(tabuleiro, ganhador);

        if (statusLinha != Status.NOTHING) {
            System.out.println(statusLinha.label + " wins");
            return true;
        }

        statusLinha = verificaColuna(tabuleiro, ganhador);

        if (statusLinha != Status.NOTHING) {
            System.out.println(statusLinha.label + " wins");
            return true;
        }

        statusLinha = verificaDiagonal(tabuleiro, ganhador);

        if (statusLinha != Status.NOTHING) {
            System.out.println(statusLinha.label + " wins");
            return true;
        }

        return false;
    }

    private static int col(int x) {
        if (x == 1) {
            return 0;
        }

        if (x == 2) {
            return 1;
        }

        if (x == 3) {
            return 2;
        }

        return 0;
    }

    private static int line(int x) {
        if (x == 1) {
            return 2;
        }

        if (x == 2) {
            return 1;
        }

        if (x == 3) {
            return 0;
        }

        return 0;
    }


    private static boolean verifyStatusGame(String[][] tabuleiro) {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (tabuleiro[x][y].equals(" ")) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void showCamp(String[][] tabuleiro) {
        System.out.println("\n");
        System.out.println("--------");
        System.out.print("| ");
        System.out.print(tabuleiro[0][0]);
        System.out.print(" ");
        System.out.print(tabuleiro[0][1]);
        System.out.print(" ");
        System.out.print(tabuleiro[0][2]);
        System.out.println(" |");

        System.out.print("| ");
        System.out.print(tabuleiro[1][0]);
        System.out.print(" ");
        System.out.print(tabuleiro[1][1]);
        System.out.print(" ");
        System.out.print(tabuleiro[1][2]);
        System.out.println(" |");

        System.out.print("| ");
        System.out.print(tabuleiro[2][0]);
        System.out.print(" ");
        System.out.print(tabuleiro[2][1]);
        System.out.print(" ");
        System.out.print(tabuleiro[2][2]);
        System.out.println(" |");
        System.out.println("--------");
    }

    private static Status verificaLinha(String[][] tabuleiro, String ganhador) {

        boolean linhasIguais;
        boolean saiuResultado = false;

        for (int x = 0; x < 3; x++) {

            if (tabuleiro[x][0].equals(tabuleiro[x][1]) && tabuleiro[x][1].equals(tabuleiro[x][2])) {
                linhasIguais = !tabuleiro[x][0].equals(" ");
                if (linhasIguais) {
                    ganhador = tabuleiro[x][0];
                }
            } else {
                linhasIguais = false;
            }

            if (saiuResultado && linhasIguais) {
                return Status.IMPOSSIBLE;
            }

            if (linhasIguais) {
                saiuResultado = true;
            }
        }

        if (saiuResultado) {
            return ganhador.equals("X") ? Status.X : Status.O;
        }

        return Status.NOTHING;
    }

    private static Status verificaColuna(String[][] tabuleiro, String ganhador) {

        boolean linhasIguais = false;
        boolean saiuResultado = false;

        for (int x = 0; x < 3; x++) {
            if (tabuleiro[0][x].equals(tabuleiro[1][x]) && tabuleiro[1][x].equals(tabuleiro[2][x])) {
                linhasIguais = !tabuleiro[0][x].equals(" ");
                if (linhasIguais) {
                    ganhador = tabuleiro[0][x];
                }
            } else {
                linhasIguais = false;
            }

            if (saiuResultado && linhasIguais) {
                return Status.IMPOSSIBLE;
            }

            if (linhasIguais) {
                saiuResultado = true;
            }
        }

        if (saiuResultado) {
            return ganhador.equals("X") ? Status.X : Status.O;
        }

        return Status.NOTHING;
    }

    /**
     * Responsavel por buscar resultado em Diagonais
     *
     * @param tabuleiro Tabuleiro do jogo
     * @return Se possui algum ganhandor em Diagonais do tabuleiro
     */
    private static Status verificaDiagonal(String[][] tabuleiro, String ganhandor) {
        Status primeiraDiagonal = diagonal(tabuleiro[0][0], tabuleiro[1][1], tabuleiro[2][2], ganhandor);
        Status segundaDiagonal = diagonal(tabuleiro[0][2], tabuleiro[1][1], tabuleiro[2][0], ganhandor);

        if (primeiraDiagonal != Status.NOTHING && segundaDiagonal != Status.NOTHING) {
            return Status.IMPOSSIBLE;
        }

        if (primeiraDiagonal != Status.NOTHING) {
            return primeiraDiagonal;
        }

        if (segundaDiagonal != Status.NOTHING) {
            return segundaDiagonal;
        }

        return Status.NOTHING;
    }

    /**
     * Responsavel por verificar resultado na primeira diagonal
     *
     * @param pos1 posicao 1 da diagonal
     * @param pos2 posicao 2 da diagonal
     * @param pos3 posicao 3 da diagonal
     * @return Se eles forem iguais (exceto em caso de _)
     */
    private static Status diagonal(String pos1, String pos2, String pos3, String ganhador) {
        if (pos1.equals(pos2) && pos2.equals(pos3) && !pos1.equals(" ")) {
            ganhador = pos1;
        }

        if (ganhador == null) {
            return Status.NOTHING;
        }

        return ganhador.equals("X") ? Status.X : Status.O;
    }
}
