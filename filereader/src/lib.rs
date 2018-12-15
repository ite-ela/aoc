use std::io::prelude::*;
use std::io::BufReader;
use std::fs::File;
use std::io::Lines;
use std::path::Path;
use std::error::Error;

pub struct FileReader {
    path: String,
}

impl FileReader {
    pub fn new(path: &str) -> FileReader {
        FileReader {
            path: path.to_string(),
        }
    }

    fn file(&self) -> File {
        let path = Path::new(&self.path);
        let display = path.display();

        match File::open(path) {
            Err(why) => panic!("couldn't open {}: {}", display,
                               why.description()),
            Ok(file) => file,
        }
    }

    pub fn read_line_by_line(&self) -> Lines<BufReader<File>> {
        BufReader::new(self.file()).lines()
    }

    pub fn read_all_lines(&self) -> Vec<String> {
        self.read_line_by_line()
            .map(|l| l.expect("Could not parse line"))
            .collect()
    }

    pub fn read_all_characters(&self) -> Vec<char> {
        let mut contents = String::new();
        self.file().read_to_string(&mut contents).unwrap();
        contents.chars().collect()
    }
}
