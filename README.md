This is the original interpreter for the esolang [Mepytaruon](https://esolangs.org/wiki/Mepytaruon).

This interpreter can be used in other JVM applications with custom stdin/stdout. To see how to start a Mepytaruon
program, look at ```main.kt``` and ```miklenda.marek.mepytaruon.control.Environment```.

```
usage: [-h] [-x X] [-y Y] [--start-up] [-c CELLS] [-t] PROGRAM

optional arguments:
-h, --help       show this help message and exit

-x X             starting x coordinate

-y Y             starting y coordinate

--start-up,      starting walking direction
--start-right,
--start-down,
--start-left

-c CELLS,        number of memory cells
--cells CELLS

-t, --trace      enable debug mode


positional arguments:
PROGRAM          file to execute
```