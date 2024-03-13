package com.example.diary.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.diary.model.Entry;
import com.example.diary.model.User;
import com.example.diary.repository.EntryRepository;
import com.example.diary.repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EntryRepository entryRepository;
    
    
    public UserController(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }
    

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        
    	if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return "./login";
        }
    	
    	User user = new User(username, password);
        userRepository.save(user);
        return "./login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model) {
        
    	if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return "./login";
        }
    	
    	User user= userRepository.findByUsernameAndPassword(username, password);
        if (user != null) {
        	List<Entry> entries = entryRepository.findByUsername(username);
            model.addAttribute("username", username);
            model.addAttribute("entries", entries);
            return "./diary";
        } else {
            return "./login";
        }
    }

    
    @PostMapping("/addentry")
    public String addEntry(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        return "./addentry"; 
    }

    
    @PostMapping("/saveentry")
    public String saveEntry(@RequestParam String username, @DateTimeFormat(pattern="yyyy-MM-dd") @RequestParam Date entryDate, @RequestParam String description, Model model) {
        Entry entry = new Entry(entryDate, description, username);
        entryRepository.save(entry);
        List<Entry> entries = entryRepository.findByUsername(username);
        model.addAttribute("username", username);
        model.addAttribute("entries", entries);
        model.addAttribute("successMessage", "Entry successfully added!");
        
        return "./diary";
    }
    
    @PostMapping("/update")
    public String updateEntry(@RequestParam Long id, @RequestParam String username, Model model) {
        Optional<Entry> optionalEntry = entryRepository.findById(id);
        
        // Check if the entry exists
        if (optionalEntry.isPresent()) {
            Entry entry = optionalEntry.get();
            model.addAttribute("entry", entry);
            model.addAttribute("id", id);
            model.addAttribute("username", username);
            return "./updateentry";
        } else {
            // Handle entry not found
            return "./error"; 
        }
    }

    @PostMapping("/delete")
    public String deleteEntry(@RequestParam Long id, @RequestParam String username,Model model) {
        entryRepository.deleteById(id);
        
        List<Entry> entries = entryRepository.findByUsername(username);
        
        model.addAttribute("entries", entries);
        model.addAttribute("username", username);        
        return "./diary";
    }
    
    
    @PostMapping("/saveupdate")
    public String saveUpdate(@RequestParam Long id,
                             @RequestParam String username,
                             @RequestParam String description,
                             Model model) {
        // Check if the entry with the provided ID exists
        Optional<Entry> optionalEntry = entryRepository.findById(id);

        if (optionalEntry.isPresent()) {
            Entry entry = optionalEntry.get();
            
            // Update the entry with the new values
            entry.setDescription(description);
            
            // Save the updated entry
            entryRepository.save(entry);

            // Redirect to the diary page with updated entries
            List<Entry> entries = entryRepository.findByUsername(username);
            model.addAttribute("entries", entries);
            model.addAttribute("username", username);
            return "./diary"; 
        } else {
            return "./error"; 
        }
    }

    @PostMapping("/logout")
    public String logoutPage() {
                
        return "./login";
    }




}
