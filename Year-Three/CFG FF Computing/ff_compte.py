import sys

class FirstFollows():
    def __init__(self, name):
        self.name = name
        self.first = []
        self.follows = []
        self.eps = False

    def set_name(self, name):
        self.name = name

    def add_first(self, first):
        self.first.append(first)

    def add_follows(self, follows):
        self.follows.append(follows)

    def is_eps(self):
        self.eps = True

    def sort_first(self):
        self.first.sort(key=custom_sort_key_first_follows)

    def sort_follows(self):
        self.follows.sort(key=custom_sort_key_first_follows)

def custom_sort_key_first_follows(value):
    # Treat "$$" as the largest value, otherwise sort normally
    if value == "$$":
        return chr(255)  # Return the highest possible value for sorting
    return value

def custom_sort_key_nonterminals(value):
    if value.name == "S'":
        return 0, value.name
    return 1, value.name

def calculate_first_eps(grammar):
    nonterminals = []
    terminals = []

    for production in grammar:
        arrow_index = production.find("->")
        if arrow_index == -1:
            #print("Invalid production")
            continue

        nonterminal_name = production[:arrow_index].strip()

        # Check if the nonterminal is already in the list
        if not any(t.name == nonterminal_name for t in nonterminals):
            nonterminals.append(FirstFollows(nonterminal_name))

        # Handle the right-hand side (after the arrow)
        after_arrow = production[arrow_index + 2:].strip()

        if after_arrow == '':
            for t in nonterminals:
                if t.name == nonterminal_name:
                    t.is_eps()

        # Iterate over the symbols after the arrow
        for symbol in after_arrow:
            # Check if the symbol is already in terminals
            if not any(t.name == symbol for t in terminals):
                if not symbol.isupper():
                    if symbol == "$":
                        term = FirstFollows("$")  # Create a FirstFollows object for the terminal
                        term.add_first("$$")  # Assuming this adds the terminal itself to its 'first' set
                        terminals.append(term)  # Append it to the terminals list
                    else:
                        term = FirstFollows(symbol)  # Create a FirstFollows object for the terminal
                        term.add_first(term.name)  # Assuming this adds the terminal itself to its 'first' set
                        terminals.append(term)  # Append it to the terminals list

    progress_made = True
    while progress_made > 0:
        progress_made = False

        for production in grammar:
            continue_flag = False
            arrow_index = production.find("->")
            nonterminal_name = production[:arrow_index].strip()
            after_arrow = production[arrow_index + 2:].strip()

            for i in range(len(after_arrow)):
                for t in nonterminals:
                    if t.name == nonterminal_name:
                        for z in nonterminals:
                            if z.name == after_arrow[i]:
                                for first in z.first:
                                    if first not in t.first:
                                        t.add_first(first)
                                        progress_made = True

                        for d in terminals:
                            if d.name == after_arrow[i]:
                                for first in d.first:
                                    if first not in t.first:
                                        t.add_first(first)
                                        progress_made = True

                for t in nonterminals:
                    if t.name == after_arrow[i]:
                        if not t.eps:
                            continue_flag = True

                for t in terminals:
                    if t.name == after_arrow[i]:
                        if not t.eps:
                            continue_flag = True

                if continue_flag is True:
                    break

            if continue_flag is True:
                continue

            for t in nonterminals:
                if t.name == nonterminal_name:
                    if t.eps is not True:
                        t.eps = True
                        progress_made = True

    return nonterminals, terminals

def string_eps(string, nonterminals, terminals):
    for character in string:
        for nt in nonterminals:
            if character == nt.name:
                if not nt.eps:
                    return False
                break
        else:
            return False
    return True

def string_first(string, nonterminals, terminals):
    return_value = []
    for character in string:
        for nt in nonterminals:
            if character == nt.name:
                for f in nt.first:
                    if f not in return_value:
                        return_value.append(f)
                if not nt.eps:
                    return return_value

        for t in terminals:
            if character == t.name:
                for f in t.first:
                    if f not in return_value:
                        return_value.append(f)
                return return_value

    return return_value

def calculate_follows(grammar, nonterminals, terminals):
    progress_made = True
    while progress_made:
        progress_made = False
        for production in grammar:
            arrow_index = production.find("->")
            nonterminal_name = production[:arrow_index].strip()
            after_arrow = production[arrow_index + 2:].strip()

            for i in range(0, len(after_arrow)):
                if after_arrow[i].isupper():
                    if i + 1 in range(len(after_arrow)):
                        B = None
                        first_to_add = string_first(after_arrow[i + 1:], nonterminals, terminals)
                        for nt in nonterminals:
                            if nt.name == after_arrow[i]:
                                B = nt
                            if B is not None:
                                for first in first_to_add:
                                    if first not in B.follows:
                                        B.add_follows(first)

                        check_eps = after_arrow[i + 1:]
                        if string_eps(check_eps, nonterminals, terminals):
                            A, B = None, None
                            for nt in nonterminals:
                                if nt.name == nonterminal_name:
                                    A = nt
                                if nt.name == after_arrow[i]:
                                    B = nt

                                if A is not None and B is not None:
                                    for follow in A.follows:
                                        if follow not in B.follows:
                                            B.add_follows(follow)
                                            progress_made = True


                    if i + 1 not in range(len(after_arrow)):
                        A, B = None, None
                        for nt in nonterminals:
                            if nt.name == nonterminal_name:
                                A = nt
                            if nt.name == after_arrow[i]:
                                B = nt

                            if A is not None and B is not None:
                                for follow in A.follows:
                                    if follow not in B.follows:
                                        B.add_follows(follow)
                                        progress_made = True


def create_print_list(nonterminals):
    print_order = []
    nonterminals.sort(key=custom_sort_key_nonterminals)
    for nt in nonterminals:
        nt.sort_first()
        nt.sort_follows()

    for nt in nonterminals:
        print_order.append(nt.name)
        first_string = ', '.join(nt.first)
        follow_string = ', '.join(nt.follows)
        print_order.append(first_string)
        print_order.append(follow_string)

    return print_order

def process_txt_file(input_file, output_file):
    #Open the input file for reading
    with open(input_file, 'r') as file:
        grammar = file.readlines() # Read the entire file content
        grammar = [production.strip() for production in grammar]

        for production in grammar:
            arrow_index = production.find("->")
            if arrow_index == -1:
                print("Arrow ether doesn't exist or there is whitespace in-between")
                return

        for i in range(len(grammar)):
            grammar[i] = grammar[i].replace(" ", "")
        grammar.insert(0, "S'->S$$")
        nt_and_t = calculate_first_eps(grammar)
        calculate_follows(grammar, nt_and_t[0], nt_and_t[1])
        print_list = create_print_list(nt_and_t[0])
        with open(output_file, 'w') as of:
            for item in print_list:
                of.write(item + '\n')

def main():
    # Check if the number of argument is correct
    if len(sys.argv) != 3:
        print("Usage:$ python ff_compute.py g.txt ff.txt")
        sys.exit(1) # Exit with error code

    input_file = sys.argv[1] # First command is input file
    output_file = sys.argv[2] # Second command is output file

    # Process the input C file and save the cleaned code to output file
    process_txt_file(input_file, output_file)

if __name__ == '__main__':
    main()

#process_txt_file("g.txt", "sigma.txt")
