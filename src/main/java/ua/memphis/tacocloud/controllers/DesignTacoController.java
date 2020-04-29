package ua.memphis.tacocloud.controllers;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import ua.memphis.tacocloud.data.jdbc.IngredientRepository;
import ua.memphis.tacocloud.data.jdbc.TacoRepository;
import ua.memphis.tacocloud.entities.Ingredient;
import ua.memphis.tacocloud.entities.Ingredient.Type;
import ua.memphis.tacocloud.entities.Order;
import ua.memphis.tacocloud.entities.Taco;
import ua.memphis.tacocloud.entities.Design;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepository) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);

        Type [] types = Ingredient.Type.values();
        for (Type type:types) {
            model.addAttribute(type.toString().toLowerCase(),filterByType(ingredients,type));
        }
        return "design";
    }


    @PostMapping
    public String processDesign(@Valid Taco taco, Errors errors,@ModelAttribute Order order) {
        if(errors.hasErrors()){
            return "design";
        }

        Taco saved = tacoRepository.save(taco);
        order.addDesign(saved);

        log.info("Processing design: " + taco);
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }


}