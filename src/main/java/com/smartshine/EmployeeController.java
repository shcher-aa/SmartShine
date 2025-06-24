@Controller
@RequestMapping("/admin/employees")
public class EmployeeController {

    private final AppUserRepository appUserRepository;

    public EmployeeController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", appUserRepository.findAll());
        return "employees";
    }

    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable Long id, Model model) {
        AppUser employee = appUserRepository.findById(id).orElseThrow();
        model.addAttribute("employee", employee);
        return "employee-form";
    }

    @GetMapping("/add")
    public String addEmployeeForm(Model model) {
        model.addAttribute("employee", new AppUser());
        return "employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") AppUser employee) {
        appUserRepository.save(employee);
        return "redirect:/admin/employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        appUserRepository.deleteById(id);
        return "redirect:/admin/employees";
    }
}