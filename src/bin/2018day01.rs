mod file;

fn main() {
    let reader = file::file_reader::FileReader::new("./resources/input/2018day01");
    let mut sum = 0;
    for line in reader.lines_iterator() {
        sum += line.unwrap()
            .parse::<i32>().unwrap()
    }

    println!("{}", sum)
}
