use std::collections::HashSet;

use file_reader::FileReader;

fn main() {
    println!("part01: {}", part01());
    println!("part02: {}", part02());
}

fn reader() -> FileReader {
    return FileReader::new("../resources/input/2020day01");
}

fn part01() -> u32 {
    let integers: Vec<u32> = reader().read_all_lines()
        .into_iter()
        .map(|s| s.parse::<u32>().unwrap())
        .collect();

    let mut found: HashSet<u32> = HashSet::new();

    for x in integers {
        let search = 2020 - x;
        if found.get(&search).is_some() {
            return search * x;
        }
        found.insert(x);
    }

    0
}

fn part02() -> u32 {
    let integers: HashSet<u32> = reader().read_all_lines()
        .into_iter()
        .map(|s| s.parse::<u32>().unwrap())
        .collect();

    let mut searches: HashSet<u32> = HashSet::new();

    for x in &integers {
        let search = 2020 - x;
        searches.insert(search);
    }

    for test in searches {
        for x in &integers {
            let i = *integers.get(x).unwrap();
            if test > i && integers.get(&(test - i)).is_some() {
                return (test - i) * i * (2020 - test);
            }
        }
    }

    0
}
