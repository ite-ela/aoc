package main

import (
	"fmt"
	"io/ioutil"
	"os"

	"./util"
)

func main() {
	fmt.Print("part01: ", part01(os.Args[1]))
}

func part01(path string) int {
	dat, err := ioutil.ReadFile(path)
	util.Check(err)

	numbers := string(dat)
	prev := 'x'
	sum := 0
	for _, c := range numbers {
		if c == prev {
			sum += int(c - '0')
		}
		prev = c
	}

	if prev == rune(numbers[0]) {
		sum += int(prev - '0')
	}

	return sum
}
