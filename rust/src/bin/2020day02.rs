use file_reader::FileReader;
use regex::Regex;

fn main() {
    println!("part01: {}", part01());
    println!("part02: {}", part02());
}

fn reader() -> FileReader {
    return FileReader::new("../resources/input/2020day02");
}

fn part01() -> u32 {
    let mut valid_passwords:Vec<Password> = Vec::new();
    let passwords = get_passwords();

    for pw in passwords {
        let re = Regex::new(pw.character.to_string().as_str()).unwrap();
        let count = re.find_iter(pw.text.as_str()).count() as u32;
        if pw.minimum <= count && pw.maximum >= count {
            valid_passwords.push(pw);
        }
    }

    valid_passwords.len() as u32
}

fn get_passwords() -> Vec<Password> {
    let passwords = reader().read_all_lines()
        .into_iter()
        .map(|s| return s.split(" ").map(|s| s.to_owned()).collect::<Vec<String>>())
        .map(|x| Password {
            text: x.get(2).unwrap().to_string(),
            character: x.get(1).unwrap().chars().nth(0).unwrap(),
            minimum: x.get(0).unwrap().split("-").nth(0).unwrap().parse().unwrap(),
            maximum: x.get(0).unwrap().split("-").nth(1).unwrap().parse().unwrap()
        })
        .collect::<Vec<Password>>();
    passwords
}

fn part02() -> u32 {
    let mut valid_passwords:Vec<Password> = Vec::new();
    let passwords = get_passwords();

    for pw in passwords {
        let re = Regex::new(pw.character.to_string().as_str()).unwrap();
        let mut found = 0;
        for x in re.find_iter(pw.text.as_str()) {
            if x.start() as u32 == pw.minimum - 1 || x.start() as u32 == pw.maximum - 1 {
                found += 1;
            }
        }

        if found == 1 {
            valid_passwords.push(pw);
        }
    }

    valid_passwords.len() as u32
}

#[derive(Debug)]
struct Password {
    text: String,
    character: char,
    maximum: u32,
    minimum: u32,
}
