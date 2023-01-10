package rogueLikeTextRPG;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author NatsuNight
 */

public class rogueLikeTextRPG {

    public static Entity getEnemy(int x, int y, ArrayList<Entity> enemies) {
        for (Entity enemy : enemies) {
            if (enemy.getX() == x && enemy.getY() == y) {
                return enemy;
            }
        }
        return null;
    }



    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);

        //todo : maybe create difficult settings ????????????????????????????????
        
        //generate seed ------------------------------------------------------------------------------------------
        Random random;
        int seed;

        System.out.println("Introduz uma seed (numero) (0 para aleatorio):");
        seed = keyboard.nextInt();

        if (seed == 0) {
            seed = new Random().nextInt(1024);
            random = new Random(seed);
            System.out.println("Seed defenida de forma aleatoria: " + seed);
        } else {
            random = new Random(seed);
            System.out.println("Seed defenida: " + seed);
        }

        //generate map -----------------------------------------------------------------------------------------
        int randomNum;

        //set number of rooms
        int mapSize;
        do {
            System.out.println("Intruduz um numero impar entre 6 e 30 para o tamanho do mapa");
            mapSize = keyboard.nextInt();
            if (mapSize%2 != 0 && mapSize>4 && mapSize<30) {
                System.out.println("Tamanho do mapa defenido como "+mapSize);
            } else {
                System.out.println("Numero invalido");
            }
        } while (mapSize%2 == 0 || mapSize<4 || mapSize>30);
        
        // Create a 2D array for the map
        char[][] map = new char[mapSize][mapSize];
            
        for (int y = 0; y < mapSize; y++) {
            for (int x = 0; x < mapSize; x++) {
                // Generate a random number between 0 and 100
                randomNum = random.nextInt(100);
                
                //set room type to each room (25%chest,25%restSite,50%enemy)
                if (randomNum <= 25) {
                    map[y][x] = 'c';
                } else if (randomNum > 25 && randomNum <= 50) {
                    map[y][x] = 'r';
                } else {
                    map[y][x] = 'e';
                }
            }
        }

        // set randown room to be boss fight
        do {
            randomNum = random.nextInt(mapSize);
            map[randomNum][randomNum] = 'b';
        } while (randomNum == mapSize/2 );

        //Set the value of the middle element to s (spawn)
        map[mapSize/2][mapSize/2] = 's';

        //generate visibleMap ----------
        char[][] mapV = new char[mapSize][mapSize];

        for (int y = 0; y < mapSize; y++) {
            for (int x = 0; x < mapSize; x++) {
                mapV[y][x] = '?';
            }
        }

        //generate enemies
        ArrayList<Entity> enemies = new ArrayList<>();

        String[] enemyNames = {"Goblin", "Orc", "Troll", "Ogre", "Skeleton", "Zombie", "Mummy", "Vampire", "Werewolf", "Demon", "Dragon", "Giant", "Gargoyle", "Witch", "Warlock", "Necromancer", "Succubus", "Incubus", "Elemental", "Golem", "Hydra", "Kraken", "Medusa", "Minotaur", "Phoenix", "Pegasus", "Unicorn", "Siren", "Mermaid", "Sea Monster", "Sea Serpent", "Leviathan", "Kaiju", "Yokai", "Oni", "Tengu", "Kappa", "Onryo", "Bakemono", "Youkai", "Obake", "Kami", "Akuma", "Shaman", "Wizard", "Sorcerer", "Mage", "Warrior", "Knight", "Paladin", "Ranger", "Thief", "Assassin", "Rogue", "Monk", "Cleric", "Druid", "Bard"};

        for (int y = 0; y < mapSize; y++) {
            for (int x = 0; x < mapSize; x++) {
                if (map[y][x] == 'e') {
                    //set name
                    int index = random.nextInt(enemyNames.length);
                    String enemyName = enemyNames[index];

                    Entity enemy = new Entity("enemy", enemyName, random.nextInt(2), random.nextInt(3) + 1, x, y);
                    enemies.add(enemy);
                }
            }
        }

        //find boss and set boss
        Entity boss = null;
        for (int y = 0; y < mapSize; y++) {
            for (int x = 0; x < mapSize; x++) {
                if (map[y][x] == 'b') {
                    mapV[y][x] = 'b';

                    boss = new Entity("boss", "Boss", random.nextInt(5), random.nextInt(5) + 6, x, y);
                        enemies.add(boss);
                    break;
                }
            }
        }

        
        //some more input and game start ------------------------------------------------------------------------
        Entity player = new Entity("player", "placeholder", 0, 10, mapSize/2, mapSize/2);

        System.out.println("Onde estou? ... Qual é o meu nome? ...");
        System.out.println("(intruduz nome)");
        player.setName(keyboard.next());
        System.out.println("Verdade, chamo-me "+player.getName());
        System.out.println("*olhas ao teu redor*");

        //game loop ========================================================================================================================
        int turn = 1;
        char action;
        boolean turnIsValid = true;

        do {

            //add current location to mapV
            
            mapV[player.getY()][player.getX()] = 'o';
                
            //adicionar lados visiveis
            if (player.getY() != mapSize-1) { //if down exists
                if (mapV[player.getY()+1][player.getX()] == '?' || mapV[player.getY()+1][player.getX()] == 'o') {
                    mapV[player.getY()+1][player.getX()] = map[player.getY()+1][player.getX()];
                }   
            }
            if (player.getY() != 0) { //if up exists
                if (mapV[player.getY()-1][player.getX()] == '?' || mapV[player.getY()-1][player.getX()] == 'o') {
                    mapV[player.getY()-1][player.getX()] = map[player.getY()-1][player.getX()];
                }
            }
            if (player.getX() != mapSize-1) { //if right exists
                if (mapV[player.getY()][player.getX()+1] == '?' || mapV[player.getY()][player.getX()+1] == 'o') {
                    mapV[player.getY()][player.getX()+1] = map[player.getY()][player.getX()+1];
                }
            }
            if (player.getX() != 0) { //if left exists
                if (mapV[player.getY()][player.getX()-1] == '?' || mapV[player.getY()][player.getX()-1] == 'o') {
                    mapV[player.getY()][player.getX()-1] = map[player.getY()][player.getX()-1];
                }
            }

            turnIsValid = true;

            //print hud ---------------------------------------
            System.out.println(" ");
            //print whats happning
            boolean enemyIsAlive = false;
            Entity enemy = getEnemy(player.getX(), player.getY(), enemies);
            if (enemy != null) { //if there is a enemy ...
                if (enemy.life < 1) { //... but is dead
                    System.out.println("Esta sala tem um enimigo, mas ja esta morto");
                } else { //... and is alive
                    enemyIsAlive = true;
                    System.out.println("Esta sala tem um enimigo!");
                    System.out.print("Nome: "+ enemy.getName());
                    System.out.print(" | Vida: "+ enemy.getLife());
                    System.out.print(" | Poder: "+ enemy.getPower());
                    System.out.println(" ");

                }
                
            } else {
                // There is no enemy at the given coordinates
            }

            System.out.println(" ");
            //print map
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    System.out.print(mapV[i][j] + " ");
                }
                System.out.println();
            }
            //print stats
            System.out.println(" ");
            System.out.println("STATISTICAS");
            System.out.print("Nome: "+ player.getName());
            System.out.print(" | Vida: "+ player.getLife());
            System.out.print(" | Poder: "+ player.getPower());
            System.out.println(" | Turno: "+ turn);
            //print controlls
            System.out.println("COMANDOS");
            System.out.print("wasd: movimentação");
            System.out.print(" | q: evoluir poder");
            System.out.print(" | e: bloquear");
            System.out.print(" | r: atacar");
            System.out.print(" | f: poções");
            System.out.println(" | p: menu");
            //print legend
            System.out.println("LEGENDA");
            System.out.print("o: tu estas aqui");
            System.out.print(" | s: inicio");
            System.out.print(" | b: boss");
            System.out.print(" | e: enimigo");
            System.out.print(" | c: bau");
            System.out.println(" ");

            //gameplay -------------------------------------------
            action = keyboard.next().charAt(0);
            System.out.println(" ");
            
            double probability = 0.75; // 75% de probabilidade
            switch (action) {
                case 'w':
                    if (player.getY() != 0) { //if up exists
                        if (enemyIsAlive) {
                            if (random.nextDouble() < probability) {
                              // código com 75% de probabilidade de ser executado
                              System.out.println("Tentaste evitar o combate mas o enimigo apanhou-te!");
                              player.setLife(player.getLife()-enemy.getPower()-1);
                              enemy.setPower(0);
                            } else {
                              // código com 25% de probabilidade de ser executado
                                player.setY(player.getY()-1);
                                System.out.println("Evitaste o combate e moveste-te para cima!");
                            }

                        } else {
                            player.setY(player.getY()-1);
                            System.out.println("Moveste-te para cima!");
                        }
                        
                        
                    } else {
                        turnIsValid = false;
                        System.out.println("Não foi possivel mover para cima!");
                    }
                    break;
                case 'a':
                    if (player.getX() != 0) { //if left exists
                        if (enemyIsAlive) {
                            if (random.nextDouble() < probability) {
                              // código com 75% de probabilidade de ser executado
                              System.out.println("Tentaste evitar o combate mas o enimigo apanhou-te!");
                              player.setLife(player.getLife()-enemy.getPower()-1);
                              enemy.setPower(0);
                            } else {
                              // código com 25% de probabilidade de ser executado
                              player.setX(player.getX()-1);
                              System.out.println("Moveste-te para a esquerda!");
                            }

                        } else {
                            player.setX(player.getX()-1);
                            System.out.println("Moveste-te para a esquerda!");
                        }
                        

                    } else {
                        turnIsValid = false;
                        System.out.println("Não foi possivel mover para a esquerda!");
                    }
                
                    break;
                case 's':
                    if (player.getY() != mapSize-1) { //if down exists
                        if (enemyIsAlive) {
                            if (random.nextDouble() < probability) {
                              // código com 75% de probabilidade de ser executado
                              System.out.println("Tentaste evitar o combate mas o enimigo apanhou-te!");
                              player.setLife(player.getLife()-enemy.getPower()-1);
                              enemy.setPower(0);
                            } else {
                              // código com 25% de probabilidade de ser executado
                              player.setY(player.getY()+1);
                            System.out.println("Moveste-te para baixo!");
                            }

                        } else {
                            player.setY(player.getY()+1);
                            System.out.println("Moveste-te para baixo!");
                        }
                        

                    } else {
                        turnIsValid = false;
                        System.out.println("Não foi possivel mover para baixo!");
                    }
                    break;
                case 'd':
                    if (player.getX() != mapSize-1) { //if right exists
                        if (enemyIsAlive) {
                            if (random.nextDouble() < probability) {
                              // código com 75% de probabilidade de ser executado
                              System.out.println("Tentaste evitar o combate mas o enimigo apanhou-te!");
                              player.setLife(player.getLife()-enemy.getPower()-1);
                              enemy.setPower(0);
                            } else {
                              // código com 25% de probabilidade de ser executado
                              player.setX(player.getX()+1);
                                System.out.println("Moveste-te para a direita!");
                            }

                        } else {
                            player.setX(player.getX()+1);
                            System.out.println("Moveste-te para a direita!");
                        }
                        

                    } else {
                        turnIsValid = false;
                        System.out.println("Não foi possivel mover para a direita!");
                    }
                    break;
                case 'q':
                if (enemyIsAlive) {
                    player.setPower(player.getPower()+1);
                    System.out.println("Poder evoluido para " + player.getPower() + "!");
                    if (enemy.getPower()==0) {
                        if (player.getPower()==0) {
                            enemy.setPower(enemy.getPower()+1);
                            System.out.println("O enemigo tambem evoluiu o poder, para " + enemy.getPower() + "!");
                        } else {
                            if (random.nextDouble() < 0.5) {
                                enemy.setPower(enemy.getPower()+1);
                                System.out.println("O enemigo tambem evoluiu o poder, para " + enemy.getPower() + "!");
                              } else {
                                  System.out.println("O enemigo bloqueo-te em vão!");
                              }
  
                        }
                    } else {
                        if (player.getPower()==0) {
                            if (random.nextDouble() < 0.5) {
                                enemy.setPower(enemy.getPower()+1);
                                System.out.println("O enemigo tambem evoluiu o poder, para " + enemy.getPower() + "!");
                            } else {
                                System.out.println("O enimigo atacou-te!");
                                player.setLife(player.getLife()-enemy.getPower()-1);
                                enemy.setPower(0);
                            }
                        } else {
                            if (random.nextDouble() < 0.33) {
                                enemy.setPower(enemy.getPower()+1);
                                System.out.println("O enemigo tambem evoluiu o poder, para " + enemy.getPower() + "!");
                            } else if (random.nextDouble() > 0.66) {
                                System.out.println("O enimigo atacou-te!");
                                player.setLife(player.getLife()-enemy.getPower()-1);
                                enemy.setPower(0);
                            } else {
                                System.out.println("O enemigo bloqueo-te em vão!");
                            }
                        }

                    }
                } else {
                    System.out.println("So podes evoluir o poder se estiveres em combate!");
                    turnIsValid = false;
                }
                
                    break;
                case 'e':
                    if (enemyIsAlive) {
                        if (enemy.getPower()==0) {
                            if (player.getPower()==0) {
                                enemy.setPower(enemy.getPower()+1);
                                System.out.println("O enemigo evoluiu o poder para " + enemy.getPower() + "!");
                                System.out.println("(Seguido por uma breve risada da tua ignorancia)");

                            } else {
                                if (random.nextDouble() < 0.5) {
                                    enemy.setPower(enemy.getPower()+1);
                                System.out.println("O enemigo evoluiu o poder para " + enemy.getPower() + "!");
                                } else {
                                    System.out.println("O enemigo bloqueo-te em vão!");
                                }
                            }
                        } else {
                            if (player.getPower()==0) {
                                if (random.nextDouble() < 0.5) {
                                    enemy.setPower(enemy.getPower()+1);
                                    System.out.println("O enemigo evoluiu o poder para " + enemy.getPower() + "!");
                                } else {
                                    System.out.println("O enimigo atacou-te mas tu bloqueaste-o!");
                                    enemy.setPower(0);
                                }
                            } else {
                                if (random.nextDouble() < 0.33) {
                                    enemy.setPower(enemy.getPower()+1);
                                    System.out.println("O enemigo evoluiu o poder para " + enemy.getPower() + "!");
                                } else if (random.nextDouble() > 0.66) {
                                    System.out.println("O enimigo atacou-te mas tu bloqueaste-o!");
                                    enemy.setPower(0);
                                } else {
                                    System.out.println("O enemigo bloqueo-te em vão!");
                                }
                            }
                        }
                    } else {
                        System.out.println("Nao tens nada para bloquear!");
                        turnIsValid = false;
                    }
                        break;
                case 'r':
                    if (player.getPower()==0) {
                        System.out.println("Nao tens poder para atacar!");
                        turnIsValid = false;
                    } else if (enemyIsAlive) {
                        if (enemy.getPower()==0) {
                            if (random.nextDouble() < 0.5) {
                                enemy.setPower(enemy.getPower()+1);
                                System.out.println("O enemigo evoluiu o poder para " + enemy.getPower() + "!");
                                System.out.println("Atacaste com " + player.getPower() + " de poder!");
                                enemy.setLife(enemy.getLife() - player.getPower());
                                player.setPower(0);
                            } else {
                                System.out.println("O enemigo bloqueo-te o ataque!");
                                player.setPower(0);
                            } 
                        } else {
                            if (random.nextDouble() < 0.33) {
                                enemy.setPower(enemy.getPower()+1);
                                System.out.println("O enemigo evoluiu o poder para " + enemy.getPower() + "!");
                                System.out.println("Atacaste com " + player.getPower() + " de poder!");
                                enemy.setLife(enemy.getLife() - player.getPower());
                                player.setPower(0);
                            } else if (random.nextDouble() > 0.66) {
                                System.out.println("Atacaste com " + player.getPower() + " de poder!");
                                enemy.setLife(enemy.getLife() - player.getPower());
                                player.setPower(0);
                                System.out.println("Mas o enimigo tambem te atacou!");
                                player.setLife(player.getLife()-enemy.getPower()-1);
                                enemy.setPower(0);
                            } else {
                                System.out.println("O enemigo bloqueo-te o ataque!");
                                player.setPower(0);
                            }
                        }



                    } else {
                        System.out.println("Nao tens nada para atacar!");
                        turnIsValid = false;
                    }
                    break;
                case 'f':
                
                    break;
                case 'p':
                
                    break;
            
                default:
                turnIsValid= false;
                System.out.println("Introduz um comando valido!");
                    break;
            }

            if (turnIsValid) turn++;
            System.out.println(" ");
            
        } while (player.getLife() > 0 && boss.getLife() > 0);

        keyboard.close();
            
        if (boss.getLife() < 1) {
            System.out.println("Boa, ganhaste, derrotaste o boss!");
        } else {
            System.out.println("Perdeste mas nao faz mal, tenta outra vez e talvez diminui o tamanho do mapa se possivel");
        }

        System.out.println(" ");
        System.out.println("Este jogo durou um total de " + turn + " turnos!");
        //todo : more stats
    
    }
}