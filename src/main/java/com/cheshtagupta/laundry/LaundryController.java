package com.cheshtagupta.laundry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("laundry")
public class LaundryController {
    @Autowired
    private LaundryRepository laundryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClothRepository clothRepository;

    @GetMapping("")
    public List<Laundry> get(@RequestHeader("User") String email) {
        User user = userRepository.findByEmail(email);


        return laundryRepository.findAllByUser(user);
    }

    @PostMapping("create")
    public Laundry create(@RequestHeader("User") String email, @RequestBody Laundry laundryInput) throws IOException {
        User user = userRepository.findByEmail(email);
        Laundry laundry = new Laundry();
        laundry.setUser(user);
        laundry.setTitle(laundryInput.getTitle());
        laundry.setClothList(laundryInput.getClothList());
        laundryRepository.save(laundry);

        return laundry;
    }

    @PatchMapping("/addCloth")
    public Laundry addCloth(@RequestParam Integer laundryID, @RequestParam Integer clothID) {
        Laundry laundry = laundryRepository.findById(laundryID).orElse(null);
        Cloth cloth = clothRepository.findById(clothID).orElse(null);

        if (laundry == null || cloth == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Not Found"
            );
        }
        laundry.getClothList().add(cloth);
        laundryRepository.save(laundry);
        return laundry;
    }

    @DeleteMapping("")
    public void delete(@RequestBody Laundry laundryInput) {
        Laundry laundry = laundryRepository.findById(laundryInput.getId()).orElse(null);
        if (laundry == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Laundry not found"
            );
        }
        laundryRepository.delete(laundry);
    }
}
