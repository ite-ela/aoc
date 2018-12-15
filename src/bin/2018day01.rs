#[macro_use]
extern crate maplit;

use std::collections::BTreeMap;

use file_reader::FileReader;

fn main() {
    println!("part01: {}", part01());
    println!("part02: {}", part02());
}

fn reader() -> FileReader {
    return FileReader::new("./resources/input/2018day01");
}

fn part01() -> i32 {
    let mut sum = 0;
    for line in reader().read_line_by_line() {
        sum += line.unwrap()
            .parse::<i32>().unwrap()
    }

    return sum;
}

fn part02() -> i32 {
    let mut sum = 0;
    let mut tree_map: BTreeMap<i32, i32> = btreemap! {sum => sum};
    let lines = reader().read_all_lines();
    loop {
        for line in lines.iter() {
            sum += line
                .parse::<i32>().unwrap();
            if tree_map.contains_key(&sum) {
                return sum;
            }

            tree_map.insert(sum, sum);
        }
    }
}
