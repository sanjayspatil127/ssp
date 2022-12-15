package com.demo.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entity.Laptop;
import com.demo.exception.ResourceNotFoundException;
import com.demo.repository.LaptopRepository;

@RestController
public class LaptopController 
{
	@Autowired
	LaptopRepository lRepo;
	//https://www.javaguides.net/2019/08/spring-responseentity-using-responseentity-in-spring-application.html
	
	@GetMapping("/laptops")
	public List<Laptop> getAllLaptops()
	{
		return lRepo.findAll();
	}
	
	@GetMapping("/laptops/{id}")
	public ResponseEntity<Laptop> getLaptopById(@PathVariable long id)  throws ResourceNotFoundException
	{
		Optional <Laptop> laptop = Optional.of(lRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Laptop id not Found:"+id)));
		 
		if(laptop.isPresent())
			return new ResponseEntity<Laptop>(laptop.get(), HttpStatus.OK);
		else
			return new ResponseEntity<Laptop>(HttpStatus.NOT_FOUND);
	}
	
	
	@GetMapping("/laptops/nameandbrand")
	public ResponseEntity<List<Laptop>> getLaptopsByNameAndBrand(@RequestParam String name, 
				@RequestParam String brand)
	{ 
		return new ResponseEntity<List<Laptop>>(lRepo.findByNameAndBrand(name, brand), HttpStatus.OK);
	}
	
	@GetMapping("/laptops/brandandprice")
	public ResponseEntity<List<Laptop>> getLaptopsByBrandAndPrice(@RequestParam String brand, 
				@RequestParam BigDecimal price)
	{
		return new ResponseEntity<List<Laptop>>(lRepo.findByBrandAndPrice(brand, price), HttpStatus.OK);
	}
	
	@GetMapping("/laptops/nameorbrandorprice")
	public ResponseEntity<List<Laptop>> getLaptopsByNameOrBrandOrPrice(@RequestParam String name, 
				@RequestParam String brand,	
				@RequestParam BigDecimal price)
	{
		return new ResponseEntity<List<Laptop>>(lRepo.findByNameOrBrandOrPrice(name, brand, price), HttpStatus.OK);
	}
	
	@PostMapping("/laptops")
	public ResponseEntity<List<Laptop>> create(@RequestBody Laptop newLaptop) throws ResourceNotFoundException
	{
		Laptop laptop = lRepo.save(newLaptop);
		if (laptop == null) {
	        throw new ResourceNotFoundException("Error in creation:"+newLaptop.getId());
	    } else {
	        return new ResponseEntity<List<Laptop>>(HttpStatus.CREATED);
	    }
	}
	
	@PutMapping("/laptops/{id}")
	public ResponseEntity<List<Laptop>> updateLaptop(@RequestBody Laptop laptopDetails,
			@PathVariable long id) throws ResourceNotFoundException
	{
		Optional<Laptop> lap = Optional.of(lRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Laptop id not Found:"+id)));
		Laptop modifyLap = lap.get();

		modifyLap.setBrand(laptopDetails.getBrand()); 
		modifyLap.setDescription(laptopDetails.getDescription());
		modifyLap.setName(laptopDetails.getName());
		modifyLap.setPrice(laptopDetails.getPrice());
		lRepo.save(modifyLap);
		return new ResponseEntity<List<Laptop>>(HttpStatus.OK);
	}
	
	@DeleteMapping("/laptops/{id}")
	public ResponseEntity<List<Laptop>> deleteLaptop(@PathVariable long id) throws ResourceNotFoundException
	{
		Optional.of(lRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Laptop id not Found:"+id)));
		lRepo.deleteById(id);
		return new ResponseEntity<List<Laptop>>(HttpStatus.OK);
	} 
	
}
