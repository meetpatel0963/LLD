## Data Modelling

```text
Player
    - id: String
    - name: String
    - health: int
    - strength: int
    - attack: int
    - isAlive: boolean
    + getAttackDamage(int rolledValue):: long
    + getDefenseStrength(int rolledValue):: long
    + takeDamage(int damage):: void

Dice
    - sides: Integer 
    - randomGenerator: Random
    + roll():: int

<<PlayerPickingStrategy>>
    + getFirstPlayer():: Player
    + getNextPlayer():: Player

MinHealthFirstPlayerPickingStrategy implements PlayerPickingStrategy
    - currentPlayerIndex: Integer
    - players: List<Players>
    + getFirstPlayer():: Player
    + getNextPlayer():: Player

<<IWinningStrategy>>
    + getWinner(List<Player> players):: Player

DefaultWinningStrategy implements IWinningStrategy
     + getWinner(List<Player> players):: Player

<<IOutputPrinter>>
    + printMessage(String message):: void

SysOutOutPutPrinter implements IOutputPrinter
    + printMessage(String message):: void

GameLoop
    - players: List<Players>
    - playerPickingStrategy: PlayerPickingStrategy
    - winningStrategy: IWinningStrategy
    - attackingDice: Dice
    - defendingDice: Dice
    - printer: IOutputPrinter
    + play():: void
```
