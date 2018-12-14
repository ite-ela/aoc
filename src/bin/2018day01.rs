#[macro_use] extern crate maplit;

mod file;

fn main() {
    println!("part01: {}", part01());
    println!("part02: {}", part02());
}

fn reader() -> file::file_reader::FileReader {
    return file::file_reader::FileReader::new("./resources/input/2018day01");
}

fn part01() -> i32 {
    let mut sum = 0;
    for line in reader().lines_iterator() {
        sum += line.unwrap()
            .parse::<i32>().unwrap()
    }

    return sum;
}

fn part02() -> i32 {
    let mut sum = 0;
    let mut tree_map = btreemap!{sum => sum};
    loop {
        for line in reader().lines_iterator() {
            sum += line.unwrap()
                .parse::<i32>().unwrap();
            if tree_map.contains_key(&sum) {
                return sum;
            }

            tree_map.insert(sum, sum);
        }
    }
}
