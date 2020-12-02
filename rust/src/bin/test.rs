use ferris_says::say; // from the previous step
use std::io::{stdout, BufWriter};

fn main() {
    let stdout = stdout();
    let message = String::from("Hello fellow Rustaceans!");
    let width = message.chars().count();

    let mut writer = BufWriter::new(stdout.lock());
    say(message.as_bytes(), width, &mut writer).unwrap();

    let x:String = "hey 1 2".to_owned();
    print!("{}", x.split(" ")
        .map(|s| s.to_owned())
        .collect::<Vec<String>>()
        .get(2).unwrap());
}
