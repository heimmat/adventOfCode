# Advent of Code
This program will execute all implemented *Advent of Code* problems. 
Your personal input will be downloaded once and cached for further use.
A year and days can be specified through command option `--days` or `-d`.

**Examples:**
* `-d 2021=1` will execute the problem posted on December 1st, 2021.
* `-d 2021=1,2,3` will execute the problems posted December 1st, 2nd and 3rd of 2021.

## Prerequisites
To access your personal input, you must specify your session key for [adventofcode.com](https://adventofcode.com/) in the file `src/main/resources/session.secret`

## Development
To implement a new year, add a new package named after the year.
Implement a new `Year` subclass and add your implemented days to the `days` map.
For each new day, implement a `Day` subclass and override the `part1` and `part2` functions. 
For testing purposes, accept a parameter `debug` and pass it to the super class.

## Testing
Implement a subclass of TestDay and override the results field.