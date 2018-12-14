mod file;

fn main() {
    println!("part01: {}", part01());
    println!("part02: {}", part02());
}

fn reader() -> file::file_reader::FileReader {
    return file::file_reader::FileReader::new("./resources/input/2015day01");
}

fn part01() -> i32 {
    let mut floor = 0;
    for up_or_down in reader().read_all_characters() {
        floor += match character_to_direction(up_or_down) {
            Direction::UP => 1,
            Direction::DOWN => -1,
        };
    };

    floor
}

fn part02() -> i32 {
    let mut floor = 0;
    let mut position = 0;
    for up_or_down in reader().read_all_characters() {
        floor += match character_to_direction(up_or_down) {
            Direction::UP => 1,
            Direction::DOWN => -1,
        };
        position += 1;
        if floor == -1 {
            return position;
        }
    };

    panic!("There is no basement!")
}

enum Direction {
    UP,
    DOWN,
}

fn character_to_direction(character: char) -> Direction {
    match character {
        '(' => Direction::UP,
        ')' => Direction::DOWN,
        _ => panic!("unknown character"),
    }
}
