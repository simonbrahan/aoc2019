File.open("input.txt") do |f|
    print f.collect { |num| (num.to_i / 3).floor - 2 }.reduce(:+).to_s + "\n"
end
