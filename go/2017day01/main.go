package main

import (
	"fmt"
	"io/ioutil"
	"os"

	"../util"
)

func main() {
	fmt.Println("part01: ", part01(os.Args[1]))
	fmt.Println("part02: ", part02(os.Args[1]))
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

func part02(path string) int {
	dat, err := ioutil.ReadFile(path)
	util.Check(err)

	numbers := string(dat)
	sum := 0
	length := len(numbers)
	for i := 0; i < length; i++ {
		c := numbers[i]
		n := numbers[(i+length/2)%length]
		if n == c {
			sum += int(c - '0')
		}
	}

	return sum
}
