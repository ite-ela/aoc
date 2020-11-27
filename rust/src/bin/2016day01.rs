use file_reader::FileReader;

fn reader() -> FileReader {
    return FileReader::new("../resources/input/2016day01");
}

fn main() {
    println!("part01: {}", part01());
}

fn part01() -> i32 {
    let string = reader().contents();
    let turns = string.split(", ");

    let position: &mut Vec<i32> = &mut vec![0, 0]; // x,y
    let direction = &mut Direction::NORTH;
    for turn in turns {
        let l_or_r = turn.chars().next().unwrap();
        let step_string: String = turn.chars().skip(1).collect();
        let steps = step_string.parse::<i32>().unwrap();
        turn_to_direction(l_or_r, direction);

        step(position, direction, steps);
    }

    position[0].abs() + position[1].abs()
}

#[derive(Eq, PartialEq, Debug, Copy, Clone)]
enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST,
}

fn turn_to_direction(character: char, current: &mut Direction) {
    // -_-
    let directions = vec![
        Direction::NORTH,
        Direction::EAST,
        Direction::SOUTH,
        Direction::WEST,
    ];
    let current_pos = directions.iter().position(|&x| x == *current).unwrap() as i32;
    let mut next_pos = match character {
        'L' => current_pos - 1,
        'R' => current_pos + 1,
        _ => panic!("unknown character"),
    };

    if next_pos < 0 {
        next_pos += directions.len() as i32;
    }
    let i = next_pos as usize % (directions.len());
    *current = directions[i];
}

fn step(position: &mut Vec<i32>, direction: &mut Direction, steps: i32) {
    match *direction {
        Direction::NORTH => position[1] += steps,
        Direction::SOUTH => position[1] -= steps,
        Direction::EAST => position[0] += steps,
        Direction::WEST => position[0] -= steps,
    }
}
