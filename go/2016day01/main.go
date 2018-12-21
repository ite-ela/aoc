package main

import (
	"fmt"
	"io/ioutil"
	"os"
	"strconv"
	"strings"

	"../util"
)

func main() {
	fmt.Println("part02: ", part02(os.Args[1]))
}

type xy struct {
	x, y int
}

type state struct {
	direction, position xy
}

func part02(path string) int {
	dat, err := ioutil.ReadFile(path)
	util.Check(err)
	turns := strings.Split(string(dat), ", ")

	north := xy{0, 1}
	start := xy{0, 0}

	dude := state{direction: north, position: start}
	goal := travel(turns, &dude)

	return util.Abs(goal.x) + util.Abs(goal.y)
}

func travel(turns []string, current *state) xy {
	visited := make(map[xy]int)
	var goal, empty xy
	empty = xy{}
	for _, turn := range turns {
		*current, goal = step(*current, turn, visited)
		if goal != empty {
			return goal
		}
	}

	panic("we never visit a position twice!")
}

func step(current state, turn string, visited map[xy]int) (state, xy) {
	dist, err := strconv.Atoi(turn[1:])
	util.Check(err)

	if dist < 0 {
		panic(fmt.Errorf("invalid distance %d", dist))
	}

	var newDirection xy
	dx := current.direction.x
	dy := current.direction.y
	if turn[0] == 'L' {
		newDirection = xy{-dy, dx}
	} else if turn[0] == 'R' {
		newDirection = xy{dy, -dx}
	} else {
		panic(fmt.Errorf("invalid direction %q", turn[0]))
	}

	var newPosition = current.position
	for i := 0; i < dist; i++ {
		newPosition = xy{newPosition.x + newDirection.x,
			newPosition.y + newDirection.y}

		visited[newPosition]++
		if visited[newPosition] > 1 {
			return state{}, newPosition
		}
	}

	return state{position: newPosition, direction: newDirection}, xy{}
}
