// build.rs

extern crate fs_extra;

fn main() {
    let mut options = fs_extra::dir::CopyOptions::new(); //Initialize default values for CopyOptions
    options.overwrite = true;
    options.copy_inside = true;
    let mut from_paths = Vec::new();
    from_paths.push("../resources");
    let result = fs_extra::copy_items(&from_paths,  "target", &options);
    println!("cargo:rerun-if-changed=build.rs");
    println!("{}", result.unwrap());
}
