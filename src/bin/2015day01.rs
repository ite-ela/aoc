use file_reader::FileReader;

fn main() {
    println!("part01: {}", part01());
    println!("part02: {}", part02());
}

fn reader() -> FileReader {
    return FileReader::new("./resources/input/2015day01");
}

fn part01() -> i32 {
    let mut floor = 0;
    for up_or_down in reader().read_all_characters() {
        floor += character_to_floor_change(up_or_down);
    };

    floor
}

fn part02() -> i32 {
    let mut floor = 0;
    let characters = reader().read_all_characters();
    for i in 0..characters.len() {
        floor += character_to_floor_change(characters[i]);
        if floor == -1 {
            return i as i32;
        }
    }

    panic!("There is no basement!")
}

enum Direction {
    UP,
    DOWN,
}

fn character_to_floor_change(character: char) -> i32 {
    match character_to_direction(character) {
        Direction::UP => 1,
        Direction::DOWN => -1,
    }
}

fn character_to_direction(character: char) -> Direction {
    match character {
        '(' => Direction::UP,
        ')' => Direction::DOWN,
        _ => panic!("unknown character"),
    }
}
