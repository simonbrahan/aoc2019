def add_mass(masses)
    return [] if masses.empty?

    required_fuel = (masses[-1] / 3).floor - 2

    return masses if required_fuel <= 0

    return add_mass(masses + [required_fuel])
end

File.open("input.txt") do |f|
    print f.collect { |num| add_mass([num.to_i])[1..-1] }.flatten.reduce(:+).to_s + "\n"
end
