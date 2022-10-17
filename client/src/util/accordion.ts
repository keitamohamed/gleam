export const accordion = () => {

    let container: NodeListOf<HTMLElement> = document.querySelectorAll(".tab-section");
    let tabs: NodeListOf<HTMLElement> = document.querySelectorAll(".accordion-tab");

    tabs.forEach((tab) => {
        tab.addEventListener('click', () => {

            container.forEach(section => {
                section.dataset.actabId == tab.dataset.actabId ?
                    section.classList.add("activate_section") :
                    section.classList.remove("activate_section")
            })
        })
    })

}