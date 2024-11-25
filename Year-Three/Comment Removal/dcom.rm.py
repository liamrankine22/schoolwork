import sys

def remove_comments(code):
    lines = code.splitlines()  # Split the code into lines
    in_multi_line = False

    for idx, line in enumerate(lines):  # Use enumerate to get both index and line
        cur_line = line
        i = 0  # Reset i for each line

        while i < len(cur_line) - 1:  # Ensure you don't go out of bounds
            current_char = cur_line[i]
            next_char = cur_line[i + 1]

            # Check for the start of a single-line comment (//)
            if current_char == '/' and next_char == '/' and not in_multi_line:
                # Check if the line contains a valid substring in the comment
                comment_part = cur_line[i + 2:]
                delete_comment = find_valid_substring(comment_part)
                if delete_comment:
                    cur_line = delete_substring_from_line(cur_line, i, len(cur_line))  # Delete the comment
                    break  # Exit the loop since the comment was removed

            # Check for the start of a multi-line comment (/*)
            elif current_char == '/' and next_char == '*':
                in_multi_line = True
                # Only check the part after /* for the valid substring
                comment_part = cur_line[i + 2:]
                delete_comment = find_valid_substring(comment_part)
                if delete_comment:
                    line_num = 0  # Track how many lines the comment spans
                    num_indices = 0  # Save index to find */
                    end_comment_found = False  # Flag to indicate if the end of comment was found

                    for j in range(idx, len(lines)):  # Start from the current line and move forward
                        line_num += 1
                        print(lines[j])
                        for y in range(len(lines[j]) - 1):
                            # Look for the end of the comment (*/)
                            if lines[j][y] == '*' and lines[j][y + 1] == '/':
                                num_indices = y + 2  # Store index just after */
                                end_comment_found = True
                                break
                        if end_comment_found:
                            break  # Exit after finding */

                    # Check if the end of the multi-line comment has been found
                    if end_comment_found:
                        # If the multi-line comment starts and ends within the same line
                        if line_num == 1:
                            # Delete the comment from the current line starting at index `i`
                            # to `i + num_indices` (where `num_indices` is the length to remove after '/*')
                            cur_line = delete_substring_from_line(cur_line, i, num_indices)
                            lines[idx] = cur_line
                            continue  # Move to the next iteration of the while loop, skipping further processing

                        else:
                            # If the multi-line comment spans multiple lines
                            # Delete the remaining part of the current line starting from `i`
                            cur_line = delete_substring_from_line(cur_line, i, len(cur_line))

                            # Loop over the number of lines the comment spans
                            for a in range(line_num):
                                # If it's the last line in the multi-line comment
                                if a == line_num - 1:
                                    # Remove the comment end '*/' from the last line
                                    lines[idx + a] = delete_substring_from_line(lines[idx + a], 0, num_indices)
                                    in_multi_line = False
                                else:
                                    # Clear the entire line for lines that are completely within the comment block
                                    lines[idx + a] = ""
                                    in_multi_line = False

                    # Continue to the next iteration of the main loop, if applicable
                    lines[idx] = cur_line
                    continue

            elif current_char == '*' and next_char == '/':
                in_multi_line = False

            i += 1  # Move to the next character

        lines[idx] = cur_line  # Update the original line in the list after processing

    # Join the cleaned lines
    cleaned_code = '\n'.join(lines)
    return cleaned_code

def find_valid_substring(code):
    length = len(code)
    # Iterate through each character in the code
    for i in range(length):
        if code[i].isdigit():
            if i + 10 <= length: # Ensure we have enough characters for a 10-char substring
                substring = code[i:i+10] # Extract the substring of length 10
                if validate_date_format(substring): # Validate the format of the substring
                    return True

    return False

def validate_date_format(substring):
    # Check if the length of the substring is exactly 10 character
    if len(substring) != 10:
        return False

    # Ensure that the third and sixth characters are '/'
    if substring[2] != '/' or substring[5] != '/':
        return False

    # Check that all other characters are digits
    for i in range(10):
        if i == 2 or i == 5: # Skip the positions for the '/'
            continue
        elif not substring[i].isdigit(): # Ensure the character is a digit
            return False

    return True

def delete_substring_from_line(line, start_idx, end_idx):
    # Check if the indices are within bounds
    if start_idx < 0 or end_idx > len(line) or start_idx >= end_idx:
        print('indicies invalid')
        return line # Return the original line if indices are invalid

    # Create a new line by removing the specified substring
    new_line = line[:start_idx] + line[end_idx:]
    return new_line

def process_c_file(input_file, output_file):
    # Open the input file for reading
    with open(input_file, 'r') as file:
        code = file.read() # Read the entire file content

        deleted_comments_code = remove_comments(code) # Remove comments from the code
        print(deleted_comments_code)

    # Open the output file for writing the cleaned code
    with open(output_file, 'w') as output:
        output.write(deleted_comments_code) # Write the cleaned code to the output file


def main():
    # Check if the number of argument is correct
    if len(sys.argv) != 3:
        print("Usage:$ python dcom.rm.py inputC.cpp inputC_rm.cpp")
        sys.exit(1) # Exit with error code

    input_file = sys.argv[1] # First command is input file
    output_file = sys.argv[2] # Second command is output file

    # Process the input C file and save the cleaned code to output file
    process_c_file(input_file, output_file)

if __name__ == '__main__':
    main()

